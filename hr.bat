call mvn -B -DskipTests=true clean package
call java -DDATABASE_URL="postgres://postgres:1@localhost:5432/salesystem" -jar target/dependency/webapp-runner.jar target/*.war
