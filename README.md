# SmartWateringSystem

 Projenin amacı tarlalardaki ağaçların mobil kontrol ile sulama işleminin yapılmasıdır. Bu işlemler için Android Client,Java Server-Client, Java Client Bloklarını birbirleri ile haberleşmelerini kapsamaktadır.
Client kısmı ağaçların bulunduğu tarlalarda microişlemciler yardımıyla buradaki Nem ve Sıcaklık bilgilerini ölçümüne sağlamakta ve merkezde bulunan Server-Client ile habarlaşmeyi sağlamaktadır. Ayrıca nem ve sıcaklık ölçülerinin eşik sınırlarına ulaştıklarında otomatik olarak sulama işlemini gerçekleştirmektedir.
Server-Client bloğu kullanıcıların bilgilerini , sahip oldukları tarlaların bilgisi ve bu tarlaların geriye dönük nem ve sıcaklık bilgilerini veritabanında bulundurmaktadır.Ayrıca Android Client'den gelen komutları alarak bu komutları ilgili tarlalara gönderme işlemini yapmaktadır.
Android Client bloğu kayıtlı kullanıcıların giriş yapabildiği , var olan kullanıcıların tarla bilgilerini ekranına getirmektedir. Ayrıca kullanıcılar tarlalarını nem ve sıcaklık bilgilerini anlık öğrenebilmektedir. Bu bilgilere bakılarak tarlalara sulama komutu gönderilebilinmektedir.

                              KULLANILAN TEKNOLOJİLER 
     *Android Client Bloğu içerisinde ksoap2-android kütüpanesi kullanılmıştır. Dolayısıyla Server-Client Bloğu 
     ile haberleşmeyi sağlamaktadır.                                                                                      
     *Android AsyncTask  "Program çalıştığı süreçte arka planda Server ve Client'nın birbirleri ile haberleşmesini          
     sağlamaktadır."                                                                                                        
     *Server-Client bloğu java soap client'i kullanarak Client bloğuna komut göndermesinde kullanılmıştır.
     *Ayrıca tüm haberleşmeler Soap(XML) üzerinden yapılmıştır.
Projenin şuanda tarlalardan sıcaklık ve nem bilgilerini Cliente bildirecek olan Microişlemci Bloğu yapım aşamasındadır.
