FROM @alpine.image.base@
WORKDIR /
COPY maven/*.jar /consolidator.jar
@entrypoint.consolidator@
USER root
