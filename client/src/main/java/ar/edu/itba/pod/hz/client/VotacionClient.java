package ar.edu.itba.pod.hz.client;

import ar.edu.itba.pod.hz.DistributedMapProvider;
import ar.edu.itba.pod.hz.JobProvider;
import ar.edu.itba.pod.hz.client.Query.Query2;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class VotacionClient {
    private static final String MAP_NAME = "paso2015";

    private static final String MAP_QUERY2 = "query2";

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

//        System.out.println(client.getCluster());

        // Preparar la particion de datos y distribuirla en el cluster a trav�s
        // del IMap
        IMap<String, Citizen> myMap = client.getMap(MAP_NAME);
        try {
            VotacionReader.readVotacion(System.getProperty("inPath"), myMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ahora el JobTracker y los Workers!
//        JobTracker tracker = client.getJobTracker("default");

        // Ahora el Job desde los pares(key, Value) que precisa MapReduce
//        KeyValueSource<String, Citizen> source = KeyValueSource.fromMap(myMap);
//        Job<String, Citizen> job = tracker.newJob(source);
        tipoViviendaQuery(new JobProvider(client), new DistributedMapProvider(client));
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
//        ICompletableFuture<List<DepartmentWithPopulation>> future = job
//                                                              .mapper(new Query4MapperFactory("Santa Fe"))
//                                                              .reducer(new Query4ReducerFactory())
//                                                              .submit(new Query4Collator(7));
//        List<DepartmentWithPopulation> rta = future.get();
//
//        for (DepartmentWithPopulation departmentWithPopulation : rta) {
//            System.out.println(String.format("%s = %d", departmentWithPopulation.getDepartment(), departmentWithPopulation.getPopulation()));
//        }

        // Query 5


//      ICompletableFuture<Map<String, Long>> future = job
//                                                              .mapper(new Query5MapperFactoryPart1())
//                                                              .reducer(new Query5ReducerFactoryPart1())
//                                                              .submit();
//
//        Map<String, Long> rta = future.get();
//
//        IMap<String, Long> otherMap = client.getMap(String.format("%s:2", MAP_NAME));
//        otherMap.putAll(rta);
//
//        KeyValueSource<String, Long> source2 = KeyValueSource.fromMap(otherMap);
//        Job<String, Long> job2 = tracker.newJob(source2);
//
//        ICompletableFuture<Map<Long, List<String>>> future2 = job2
//                .mapper(new Query5MapperFactoryPart2())
//                .reducer(new Query5ReducerFactoryPart2())
//                .submit();
//
//        Map<Long, List<String>> rta2 = future2.get();
//
//        for (Map.Entry<Long, List<String>> e : rta2.entrySet()) {
//            System.out.println(String.format("%s => %s", e.getKey(), e.getValue()));
//        }


    }

    private static void tipoViviendaQuery(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {
        Map<TipoVivienda, Double> map = new Query2().execute(jobProvider, mapProvider);

        for (Map.Entry<TipoVivienda, Double> e : map.entrySet()) {
            System.out.println("Tipo vivienda: " + e.getKey() + " average: " + e.getValue());
        }
    }
}
