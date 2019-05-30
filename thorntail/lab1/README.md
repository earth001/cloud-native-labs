# Execution

`java -jar target/thorntail-inventory-lab1-1.0.0-SNAPSHOT-thorntail.jar`

or 

`mvn thorntail:run`

# Testing 

`curl http://localhost:8080/api/inventory/329299`

Sample Output

`{"itemId":"329299","location":"Raleigh","quantity":736,"link":"http://maps.google.com/?q=Raleigh"}`
