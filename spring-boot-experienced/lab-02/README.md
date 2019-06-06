# Application Architecture

* The cart microservice consists of a single Maven project, which is internally composed of a number of service objects:

* PriceCalculationService contains logic to calculate the shipping costs and total value of the shopping cart.

* CatalogService is responsible for calling the catalog service to obtain product data.

* ShoppingCartService is responsible for managing the shopping carts.

* CartEndpoint contains the REST APIs to access the cart microservice.
