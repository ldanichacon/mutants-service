# mutants-service Project

## Description

This project exposes two endpoints:
* /mutant: endpoint to validate whether a human is a mutant or not, given a NxN matrix (a DNA sequence).
* /stats: endpoint to retrieve statistics of previous analyzed DNAs.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Running tests

You can run all unit tests using:
```shell script
./mvnw test
```

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `*-runner.jar` file.

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Deploying the application

Given an installed and configured [gloud CLI](https://cloud.google.com/sdk/docs/install) and a packaged application, use the following command:

```shell script
gcloud app deploy
```

Keep in mind that in order to be deployed successfully on Google App Engine, the project should be compiled using Java 11.

## Deployed url

The application can be found here: 

https://mutantsmercadolibre-342703.uc.r.appspot.com/

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

## Technologies

### Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its [website](https://quarkus.io/)

### Google Cloud Firestore

NoSQL database allocated on Google Cloud 

For more information, please visit its [website](https://firebase.google.com/docs/firestore)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details