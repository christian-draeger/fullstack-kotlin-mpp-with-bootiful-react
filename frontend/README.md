# Kotlin React frontend

## local development
The frontend bundling will take place automatically if you start/build the whole app.
However, sometimes it can be useful to do things manually during development.
### start frontend only (webpack)
```shell script
./gradlew browserRun
```

### create production bundle
bundle (including source maps) will be placed in `frontend/build/distributions/frontend.js`
```shell script
./gradlew browserWebpack
```

## Useful links

react with kotlin readme:

https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-react

styled-components with kotlin readme:

https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-styled

redux thunk with kotlin tutorial:

https://akjaw.com/blog/kotlinjs_react_redux_with_thunk_and_material_ui