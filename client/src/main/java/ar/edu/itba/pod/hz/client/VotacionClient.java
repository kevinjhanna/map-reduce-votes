package ar.edu.itba.pod.hz.client;

import ar.edu.itba.pod.hz.client.IO.Configuration;
import ar.edu.itba.pod.hz.client.IO.Parser;
import ar.edu.itba.pod.hz.client.IO.reader.DataReader;
import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.Query.QueryExecutor;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VotacionClient {
    private static final String MAP_NAME = "paso2015";

    private static final String MAP_QUERY2 = "query2";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Configuration configuration = new Parser().getConfiguration();
        HazelcastInstance client = loadConfiguration(configuration);

        JobProvider jobProvider = new JobProvider(client);
        DistributedMapProvider mapProvider = new DistributedMapProvider(client);

        DataReader dataReader = new DataReader(configuration.getInputPath());

        QueryExecutor queryExecutor = new QueryExecutor(jobProvider, mapProvider, dataReader);

        queryExecutor.execute(configuration.getQueryID());

    }

    private static HazelcastInstance loadConfiguration(Configuration configuration) {
        ClientConfig ccfg = new ClientConfig();
        System.out.println(String.format("Connecting with cluster dev-name [%s]", configuration.getName()));

        ccfg.getGroupConfig().setName(configuration.getName()).setPassword(configuration.getPass());

        ClientNetworkConfig net = new ClientNetworkConfig();
        List<String> addresses = Arrays.asList(configuration.addresses());

        net.addAddress(addresses.toArray(new String[addresses.size()]));
        ccfg.setNetworkConfig(net);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        System.out.println(client.getCluster());

        return client;
    }
}
