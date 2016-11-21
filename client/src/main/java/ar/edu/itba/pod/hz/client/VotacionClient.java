package ar.edu.itba.pod.hz.client;

import ar.edu.itba.pod.hz.client.IO.Configuration;
import ar.edu.itba.pod.hz.client.IO.FileWriter;
import ar.edu.itba.pod.hz.client.IO.Parser;
import ar.edu.itba.pod.hz.client.IO.reader.DataReader;
import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.Query.QueryExecutor;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class VotacionClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Locale.setDefault(new Locale("es", "es-AR"));

        Logger logger = LoggerFactory.getLogger(VotacionClient.class);
        Configuration configuration = new Parser().getConfiguration(logger);

        logger.info(String.format("Connecting with cluster dev-name [%s]", configuration.getName()));
        HazelcastInstance client = loadConfiguration(configuration);
        logger.info(client.getCluster().toString());

        JobProvider jobProvider = new JobProvider(client);
        DistributedMapProvider mapProvider = new DistributedMapProvider(client);

        DataReader dataReader = new DataReader(configuration.getInputPath());

        FileWriter fileWriter = new FileWriter(configuration.getOutputPath(), logger);
        QueryExecutor queryExecutor = new QueryExecutor(jobProvider, mapProvider, dataReader, logger, fileWriter);

        queryExecutor.execute(configuration);

    }

    private static HazelcastInstance loadConfiguration(Configuration configuration) {
        ClientConfig ccfg = new ClientConfig();

        ccfg.getGroupConfig().setName(configuration.getName()).setPassword(configuration.getPass());

        ClientNetworkConfig net = new ClientNetworkConfig();
        List<String> addresses = Arrays.asList(configuration.addresses());

        net.addAddress(addresses.toArray(new String[addresses.size()]));
        ccfg.setNetworkConfig(net);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

        return client;
    }
}
