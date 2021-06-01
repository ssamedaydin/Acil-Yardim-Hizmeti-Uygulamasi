# Acil-Yardim-Hizmeti-Uygulamasi
Arduino ile ölçülen nabız, oksijen ve siceklık değerlerini bluetooth ile mobil uygulamaya aktarımı ve anormal değerlerde acil yardım hizmetlerine ulaşımın kolaylaştırılmasını sağlayan acil ihbar uygulaması.

Kullanıcının nabzı, kandaki oksijen miktarı ve vücut sıcaklığı sürekli ölçülerek mobil uygulamaya kablosuz olarak aktarılacaktır. Kullanıcı aniden yaşadığı olaylarda veya doğal afetlerde nabzı yükselecek ya da kalp krizi, salgın hastalık gibi durumlarda kandaki oksijen miktarı ve vücut sıcaklığı değişecektir. Bu veriler standartların dışına çıktığında mobil uygulama sıra dışı bir durum olduğunu algılayarak kullanıcıya tek bir tuş ile polis, ambülans ya da itfaiye gibi acil yardım servislerine ulaşımı sağlayacaktır. Aynı zamanda uygulama, duman sensörüne bağlı çalışacak ve belirli bir seviyede duman algılaması durumunda kullanıcıyı uyaracaktır. 

Google Map Api kullanılmıştır.
Konum etrafındaki mekanları JSON olarak web servis ile çeklip listelenmiştir.
Bluetooth modeli yazılarak arduino ve mobil uygulama arasında kablosuz veri iletişimi sağlanmıştır.
