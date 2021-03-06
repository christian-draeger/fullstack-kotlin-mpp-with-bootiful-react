# fullstack-kotlin-mpp-with-bootiful-react
An all Kotlin fullstack app that consists of a React.js frontend and a spring-boot backend, bundled together as ONE deployable fatjar aka
**how to build a modern web application using Kotlin only!**
Nevertheless, there are also specific artifacts of the frontend and backend that could be deployed separately.

## Start complete Application
```shell script
./gradlew start
```

## Run Frontend standalone via webpack (useful during development process)
```shell script
./gradlew browserRun --continuous
```

## Run backend only (useful during development process)
```shell script
./gradlew bootRun
```

## Check for available dependency updates
```shell script
./gradlew dependencyUpdates
```

## Apply code style to idea
```shell script
./gradlew ktlintApplyToIdea
```