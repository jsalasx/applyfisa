# Apply Fisa

## Requerimientos 

### JAVA 17
### Puerto 8088 libre
### Docker

## Compilar el proyecto
```shell script
./mvnw package
```

## Crear la Imagen de Docker
```shell script
docker build -f .\\src\\main\\docker\\Dockerfile.jvm -t applyfisa
```

## Correr la imagen de Docker
```shell script
docker run -d -p 8088:8080 applyfisa
```

## Pruebas

Para probar el proyecto el repositorio tiene un archivo de postman con los endpoins solicitados.

```shell script
Fisa.postman_collection.json
```



