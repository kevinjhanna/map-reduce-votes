echo "Generating maven project"
mvn clean package

echo "Generating node scripts"
tar -xf server/target/clase09-ejer01-server-1.0-SNAPSHOT-bin.tar.gz -C server/target
chmod +x server/target/clase09-ejer01-server-1.0-SNAPSHOT/run-node.sh

echo "Generating client scripts"
tar -xf client/target/clase09-ejer01-client-1.0-SNAPSHOT-bin.tar.gz -C client/target
chmod +x client/target/clase09-ejer01-client-1.0-SNAPSHOT/run-client.sh