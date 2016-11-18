package ar.edu.itba.pod.hz.client;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

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

        JobProvider jobProvider = new JobProvider(client);
        DistributedMapProvider mapProvider = new DistributedMapProvider(client);

        // Query 4
//        List<DepartmentWithPopulation> answer = new Query4().execute(jobProvider, mapProvider);
//
//        for (DepartmentWithPopulation departmentWithPopulation : answer) {
//            System.out.println(String.format("%s = %d", departmentWithPopulation.getDepartment(), departmentWithPopulation.getPopulation()));
//        }

        // Query 5
//        Map<Long, List<String>> answer = new Query5().execute(jobProvider, mapProvider);
//
//        for (Map.Entry<Long, List<String>> e : answer.entrySet()) {
//            System.out.println(String.format("%s => %s", e.getKey(), e.getValue()));
//        }

    }
}
