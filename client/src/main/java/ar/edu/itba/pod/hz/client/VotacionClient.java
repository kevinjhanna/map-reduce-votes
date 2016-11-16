package ar.edu.itba.pod.hz.client;

import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithPopulation;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import ar.edu.itba.pod.hz.mr.*;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class VotacionClient {
    private static final String MAP_NAME = "paso2015";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String name = System.getProperty("name");
        String pass = System.getProperty("pass");
        if (pass == null) {
            pass = "dev-pass";
        }
        System.out.println(String.format("Connecting with cluster dev-name [%s]", name));

        ClientConfig ccfg = new ClientConfig();
        ccfg.getGroupConfig().setName(name).setPassword(pass);

        // no hay descubrimiento automatico,
        // pero si no decimos nada intentará usar LOCALHOST
        String addresses = System.getProperty("addresses");
        if (addresses != null) {
            String[] arrayAddresses = addresses.split("[,;]");
            ClientNetworkConfig net = new ClientNetworkConfig();
            net.addAddress(arrayAddresses);
            ccfg.setNetworkConfig(net);
        }
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(client.getCluster());

        // Preparar la particion de datos y distribuirla en el cluster a trav�s
        // del IMap
        IMap<String, Citizen> myMap = client.getMap(MAP_NAME);
        try {
            VotacionReader.readVotacion(myMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ahora el JobTracker y los Workers!
        JobTracker tracker = client.getJobTracker("default");

        // Ahora el Job desde los pares(key, Value) que precisa MapReduce
        KeyValueSource<String, Citizen> source = KeyValueSource.fromMap(myMap);
        Job<String, Citizen> job = tracker.newJob(source);
        // // Orquestacion de Jobs y lanzamiento
//        ICompletableFuture<Map<String, Integer>> future = job
//                                                                  .mapper(new Query1MapperFactory())
//                                                                  .reducer(new Query1ReducerFactory())
//                                                                  .submit();
//
//        // Tomar resultado e Imprimirlo
//        Map<String, Integer> rta = future.get();
//
//        for (Map.Entry<String, Integer> e : rta.entrySet()) {
//            System.out.println(String.format("Rango %s => %s", e.getKey(), e.getValue()));
//        }

        // Query 3

//        ICompletableFuture<List<DepartmentWithIndex>> future = job
//                                                              .mapper(new Query3MapperFactory())
//                                                              .reducer(new Query3ReducerFactory())
//                                                              .submit(new TopCollator(10));
//
//        // Tomar resultado e Imprimirlo
//        List<DepartmentWithIndex> rta = future.get();
//
//        for (DepartmentWithIndex departmentWithIndex : rta) {
//            System.out.println(String.format("%s = %.2f",
//            departmentWithIndex.getDepartment(), departmentWithIndex.getIndex()));
//        }

        // Query 4
        ICompletableFuture<List<DepartmentWithPopulation>> future = job
                                                              .mapper(new Query4MapperFactory("Santa Fe"))
                                                              .reducer(new Query4ReducerFactory())
                                                              .submit(new Query4Collator(7));
        List<DepartmentWithPopulation> rta = future.get();

        for (DepartmentWithPopulation departmentWithPopulation : rta) {
            System.out.println(String.format("%s = %d", departmentWithPopulation.getDepartment(), departmentWithPopulation.getPopulation()));
        }

    }

    private static void tipoViviendaQuery(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
        ICompletableFuture<Map<TipoVivienda, Double>> future = job
                .mapper(new Query2MapperFactory())
                .reducer(new Query2ReducerFactory())
                .submit(new AverageCollator());

        Map<TipoVivienda, Double> rta = future.get();

        for (Map.Entry<TipoVivienda, Double> e : rta.entrySet()) {
            System.out.println(String.format("Rango %s => %.2f%%", e.getKey(), e.getValue()));
        }
        System.exit(0);
    }
}
