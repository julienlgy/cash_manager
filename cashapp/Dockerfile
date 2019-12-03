FROM ubuntu:latest

RUN apt-get update && \
    apt-get -y upgrade && \
    apt-get -y install \
    apache2 

COPY ./www/ /var/www/html/
COPY ./app/build/outputs/apk/debug/app-debug.apk /var/www/html/client.apk
RUN rm -rf /etc/apache2/sites-enabled/000-default.conf
EXPOSE 80
CMD /usr/sbin/apache2ctl -D FOREGROUND