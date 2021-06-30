# Acil-Yardim-Hizmeti-Uygulamasi
Arduino ile ölçülen nabız, oksijen ve sicaklık değerlerini bluetooth ile mobil uygulamaya aktarımı ve anormal değerlerde acil yardım hizmetlerine ulaşımın kolaylaştırılmasını sağlayan bir acil ihbar uygulamasıdır.

Kullanıcının nabzı, kandaki oksijen miktarı ve vücut sıcaklığı sürekli ölçülerek mobil uygulamaya kablosuz olarak aktarılmaktadır. Kullanıcı aniden yaşadığı olaylarda veya doğal afetlerde nabzı yükselecek ya da kalp krizi, salgın hastalık gibi durumlarda kandaki oksijen miktarı ve vücut sıcaklığı değişecektir. Bu veriler standartların dışına çıktığında mobil uygulama sıra dışı bir durum olduğunu algılayarak kullanıcıya tek bir tuş ile polis, ambülans ya da itfaiye gibi acil yardım servislerine ulaşımı sağlamaktadır. Aynı zamanda uygulama, duman sensörüne bağlı çalışmakta ve belirli bir seviyede duman algılaması durumunda kullanıcıyı uyarmaktadır. 

Google Maps Api kullanılmıştır.
Konum etrafındaki mekanları JSON olarak web servis ile çeklip listelenmiştir.
Bluetooth modeli yazılarak arduino ve mobil uygulama arasında kablosuz veri iletişimi sağlanmıştır.

![tasarim](https://user-images.githubusercontent.com/75077248/123960427-f35af880-d9b7-11eb-94f7-181bd8e5ec2c.png)
