# Используем официальный Debian-подобный образ OpenJDK 22
FROM openjdk:22-jdk-bullseye

# Установим необходимые переменные окружения для Tomcat
ENV TOMCAT_VERSION=9.0.65
ENV TOMCAT_HOME=/opt/tomcat

# Установим зависимости и создадим пользователя для Tomcat
RUN apt-get update && \
    apt-get install -y curl tar && \
    mkdir -p "$TOMCAT_HOME" && \
    useradd -m -d "$TOMCAT_HOME" -U -s /bin/bash tomcat && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Скачиваем и устанавливаем Apache Tomcat
RUN curl -fSL https://archive.apache.org/dist/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz \
    | tar -xzC /opt/tomcat --strip-components=1

# Установим необходимые права доступа
RUN chown -R tomcat:tomcat /opt/tomcat

# Переключимся на пользователя tomcat
USER tomcat

# Удаляем дефолтное приложение ROOT (опционально)
RUN rm -rf $TOMCAT_HOME/webapps/ROOT

# Копируем ваш WAR-файл в папку webapps Tomcat
COPY target/textFileManager.war $TOMCAT_HOME/webapps/ROOT.war

# Открываем порт 8080
EXPOSE 8080

# Указываем рабочую директорию и переменные окружения
WORKDIR $TOMCAT_HOME
ENV CATALINA_HOME=$TOMCAT_HOME

# Запускаем Tomcat
CMD ["bin/catalina.sh", "run"]
