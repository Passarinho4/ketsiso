FROM java:8

EXPOSE 8080
RUN mkdir -p /application/static/results
ADD simulationlauncher /application/
ADD index.html /application

ENTRYPOINT java -cp /application com.tegess.ketsiso.simulationlauncher.Launcher
