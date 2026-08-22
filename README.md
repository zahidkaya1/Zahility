<div align="center">

# ❄️ Zahility

### Minecraft için dengeli yardımcı araçlar, özel üretim sistemleri ve işlevsel kar topları

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-62B47A?style=for-the-badge&logo=minecraft)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.244-E68A2E?style=for-the-badge)](https://neoforged.net/)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)](https://www.java.com/)
[![Sürüm](https://img.shields.io/badge/Sürüm-0.1.0-8A5CF6?style=for-the-badge)](https://github.com/zahidkaya1/Zahility)
[![Durum](https://img.shields.io/badge/Durum-Geliştiriliyor-3498DB?style=for-the-badge)](https://github.com/zahidkaya1/Zahility)

**Zahility**, Minecraft Java Edition için geliştirilen bir **Vanilla+ ve Quality of Life** modudur.

[Özellikler](#-özellikler) •
[Kar Topları](#-özel-kar-topları) •
[Zahility Tezgâhı](#-zahility-tezgâhı) •
[Üretim Sistemi](#-üretim-sistemi) •
[Kurulum](#-kurulum)

</div>

---

## 📖 Zahility Nedir?

Zahility; Minecraft'taki zaman alan, tekrarlanan veya zahmetli işlemleri daha kullanışlı hâle getirmeyi amaçlayan bir yardımcı moddur.

Modun temel amacı oyunu tamamen kolaylaştırmak değil, oyuncuya belirli görevlerde yardımcı olacak **dengeli ve anlamlı araçlar** sunmaktır. Güçlü özellikler daha pahalı tarifler, özel üretim aşamaları ve oyun sonu malzemeleri gerektirir.

Zahility şu anda özel kar topları ve kendine ait üretim sistemi üzerine kuruludur. İlerleyen sürümlerde mod yalnızca kar toplarıyla sınırlı kalmayacak; yeni yardımcı araçlar, bloklar, malzemeler ve farklı Quality of Life mekanikleri de eklenecektir.

---

## 🎯 Projenin Amaçları

Zahility geliştirilirken şu temel kurallar dikkate alınır:

- Minecraft'ın Vanilla oynanış yapısını mümkün olduğunca korumak
- Tekrarlanan işlemleri daha pratik hâle getirmek
- Survival ilerleyişini anlamsızlaştırmamak
- Güçlü özellikleri pahalı ve aşamalı üretimlerle dengelemek
- Her yardımcı araca anlaşılır görsel ve ses geri bildirimi vermek
- Vanilla eşyaların davranışlarını bozmamak
- Tek oyunculu ve çok oyunculu dünyalarda kullanılabilecek bir yapı oluşturmak
- Modu ileride yeni yardımcı sistemlerle genişletebilmek

---

## ✨ Özellikler

Zahility'nin mevcut geliştirme sürümünde şunlar bulunmaktadır:

- Altı farklı özel kar topu ailesi
- Her kar topunun normal ve Sonsuz sürümü
- Özel 3×3 üretim sistemi
- Zahility Tezgâhı
- Vanilla tarzı özel tarif kitabı
- Tarif arama sistemi
- Dört farklı tarif kategorisi
- İlk üretim, çoğaltma ve Sonsuz geliştirme aşamaları
- Türkçe ve İngilizce dil desteği
- Özelleştirilmiş parçacıklar ve sesler
- Oyuncuya bilgi veren çevrilmiş işlem mesajları
- Vanilla kar toplarından tamamen bağımsız davranış sistemi
- Snow Golem kar toplarına karşı koruma
- Kendine özgü eşya ve blok görselleri

---

# ☃️ Özel Kar Topları

Zahility'de şu anda altı farklı özel kar topu bulunmaktadır:

| Kar Topu | Temel görevi | Normal etki alanı | Sonsuz etki alanı |
|---|---|---:|---:|
| Topraklaştırıcı | Uygun doğal blokları Dirt'e dönüştürür | Yaklaşık 3 blok yarıçap | Yaklaşık 8 blok yarıçap |
| Düzleştirici | Araziyi seçilen Y seviyesine getirir | 7×7 | 15×15 |
| Sünger | Bölgedeki suyu temizler | 5×5×5 | 11×11×11 |
| Dondurucu | Bölgedeki suyu buza dönüştürür | 5×5×5 | 11×11×11 |
| Yeşertici | Uygun bitkileri büyütür | 5×5×5 | 11×11×11 |
| Kovucu | Yakındaki düşman yaratıkları ortadan kaldırır | Sınırlı alan | Genişletilmiş alan |

## Kar Toplarının Ortak Kuralları

### Normal sürümler

- Survival oynanışında üretilebilir.
- Kullanıldığında bir adet tüketilir.
- En fazla 16 adet üst üste taşınabilir.
- Zahility Tezgâhında ilk kez üretilebilir.
- Daha sonra özel çoğaltma tarifleriyle çoğaltılabilir.

### Sonsuz sürümler

- Kullanıldığında tüketilmez.
- En fazla bir adet taşınabilir.
- Normal sürümle aynı ana görseli kullanır.
- Büyü parıltısına sahiptir.
- Normal sürümden daha geniş veya daha güçlü etki oluşturur.
- Sonsuzluk Çekirdeği ve Netherite Block gerektirir.
- Oyun sonu için tasarlanmış pahalı eşyalardır.

> Sonsuz sürümlerin kayıt kimliklerinde mevcut dünyalarla uyumluluğu korumak için `creative_` ön eki kullanılmaya devam etmektedir.

### Vanilla davranışları

Aşağıdaki kar topları Zahility özelliklerini tetiklemez:

- Normal Minecraft kar topları
- Snow Golem tarafından atılan kar topları
- Başka modlara ait farklı fırlatılabilir eşyalar

Yalnızca Zahility tarafından kaydedilmiş özel kar topları kendi davranışlarını çalıştırır.

---

## 🌱 Topraklaştırıcı Kar Topu

Uygun doğal blokları etki alanı içerisinde Dirt bloğuna dönüştürür.

### Eşya kimlikleri

```text
zahility:terraform_snowball
zahility:creative_terraform_snowball
```

### Özellikleri

- Normal sürüm yaklaşık 3 blok yarıçapında çalışır.
- Sonsuz sürüm yaklaşık 8 blok yarıçapında çalışır.
- Dönüştürülebilen bloklar veri etiketiyle yönetilir.
- `#zahility:terraformable_blocks` etiketini kullanır.
- End Stone ve Clay gibi desteklenen blokları Dirt'e dönüştürür.
- İşlem sırasında parçacık ve ses efekti oluşturur.
- Desteklenmeyen bloklara dokunmaz.

---

## ⛏️ Düzleştirici Kar Topu

Araziyi oyuncunun daha önce seçtiği Y seviyesine göre düzleştirir.

### Eşya kimlikleri

```text
zahility:leveling_snowball
zahility:creative_leveling_snowball
```

### Kullanımı

1. Düzleştirici Kar Topunu eline al.
2. Eğilerek bir bloğa sağ tıkla.
3. Bloğun Y seviyesi düzleme seviyesi olarak kaydedilir.
4. Kar topunu düzenlemek istediğin araziye at.

### Özellikleri

- Normal sürüm 7×7 alanı etkiler.
- Sonsuz sürüm 15×15 alanı etkiler.
- Hedef seviyenin üzerindeki araziyi temizler.
- Hedef seviyenin altındaki boşlukları Dirt ile doldurur.
- Hedef yüzeyi Grass Block yapar.
- Yüzeyin altındaki yakın boşlukları altı bloğa kadar destekler.
- Derin mağaralardan etkilenmez.
- Derin yeraltı suyunu engel olarak değerlendirmez.
- Hedef seviye ve üzerindeki su sütunlarını korur.
- Sandık ve fırın gibi Block Entity içeren sütunları korur.
- Desteklenen bitki örtüsünü temizleyebilir.
- Seçilen seviye ve işlem sonucu hakkında bilgi mesajı gösterir.

---

## 💧 Sünger Kar Topu

Etki alanındaki suyu temizleyen taşınabilir bir sünger görevi görür.

### Eşya kimlikleri

```text
zahility:sponge_snowball
zahility:creative_sponge_snowball
```

### Özellikleri

- Normal sürüm 5×5×5 alanı etkiler.
- Sonsuz sürüm 11×11×11 alanı etkiler.
- Durgun suyu temizler.
- Akan suyu temizler.
- Waterlogged blokları yok etmez.
- Waterlogged özelliğini `false` yaparak bloğun içindeki suyu kaldırır.
- Lavaya dokunmaz.
- Ice, Packed Ice ve Blue Ice bloklarına dokunmaz.
- Splash ve Bubble parçacıkları kullanır.
- Vanilla Sponge emme sesini kullanır.

> Büyük ve açık su kaynaklarında etki alanının dışındaki suyun tekrar içeri akması Minecraft'ın normal sıvı davranışıdır.

---

## 🧊 Dondurucu Kar Topu

Etki alanındaki durgun ve akan suyu Ice bloğuna dönüştürür.

### Eşya kimlikleri

```text
zahility:freezing_snowball
zahility:creative_freezing_snowball
```

### Özellikleri

- Normal sürüm 5×5×5 alanı etkiler.
- Sonsuz sürüm 11×11×11 alanı etkiler.
- Durgun suyu Ice'a dönüştürür.
- Akan suyu Ice'a dönüştürür.
- Waterlogged bloklara dokunmaz.
- Lavaya dokunmaz.
- Mevcut Ice bloklarını değiştirmez.
- Packed Ice ve Blue Ice bloklarını değiştirmez.
- Kar tanesi parçacıkları oluşturur.
- Donma hissi veren bir ses efekti kullanır.

---

## 🌿 Yeşertici Kar Topu

Vanilla kemik tozu davranışını geniş bir alandaki uygun bitkilere uygular.

### Eşya kimlikleri

```text
zahility:growth_snowball
zahility:creative_growth_snowball
```

### Özellikleri

- Normal sürüm 5×5×5 alanı etkiler.
- Sonsuz sürüm 11×11×11 alanı etkiler.
- Vanilla kemik tozu sistemini destekleyen bitkilerde çalışır.
- Minecraft'ın normal kemik tozu başarı ihtimalini korur.
- Tamamen büyümüş bitkilere gereksiz işlem uygulamaz.
- Sonsuz sürüm her uygun hedefte daha fazla büyütme denemesi yapar.
- Yeşil parçacıklar oluşturur.
- Vanilla Bone Meal kullanım sesini kullanır.

---

## 👻 Kovucu Kar Topu

Belirlenen alan içerisindeki düşman yaratıklarını ortadan kaldırmak için kullanılır.

### Eşya kimlikleri

```text
zahility:repelling_snowball
zahility:creative_repelling_snowball
```

### Özellikleri

- Yakındaki düşman yaratıkları hedefler.
- Pasif hayvanlara dokunmaz.
- Normal ve Sonsuz sürümlerin etki alanları farklıdır.
- Vanilla kar toplarından etkilenmez.
- Snow Golem kar toplarından etkilenmez.
- Başarılı işlem sonrasında parçacık ve ses oluşturur.
- Etkilenen hedef sayısını oyuncuya bildirir.

Kovucu Kar Topu, gece ortaya çıkan düşmanları temizlemeyi kolaylaştırırken bütün bölgeyi kalıcı olarak güvenli hâle getirmez. Yeni düşmanlar normal Minecraft kurallarına göre tekrar doğabilir.

---

# 🛠️ Zahility Tezgâhı

Zahility Tezgâhı, mod içerisindeki özel eşyaların üretildiği 3×3 üretim istasyonudur.

Tezgâhın kendisi normal Crafting Table içerisinde üretilebilir. Diğer özel Zahility tarifleri yalnızca bu tezgâhta çalışır.

## Tezgâh özellikleri

- 3×3 üretim alanı
- Bir sonuç yuvası
- Vanilla Crafting Table görünümüne yakın arayüz
- Özel Zahility tarif kitabı
- Tarif arama kutusu
- Tarif kategorileri
- Tariflerin otomatik yerleştirilmesi
- Eksik malzemeleri gösteren hayalet tarif desteği
- Shift + tıklama desteği
- Oyuncu tezgâhtan uzaklaşınca menünün kapanması
- Menü kapanınca giriş eşyalarının oyuncuya geri verilmesi
- Yerleştiren oyuncuya doğru dönen ön yüz
- Balta ile hızlı kırılma
- Survival modunda kırıldığında kendisini düşürme
- Özel üst, ön ve yan yüz görselleri

## Tarif kitabı kategorileri

Zahility tarif kitabı tarifleri dört bölüme ayırır:

| Kategori | İçerik |
|---|---|
| Malzemeler | Özel Kar Topu Şablonu ve Sonsuzluk Çekirdeği |
| İlk Üretim | Özel bir kar topunun ilk kez üretilmesi |
| Çoğaltma | Mevcut özel kar topunun çoğaltılması |
| Sonsuz | Normal kar topunun Sonsuz sürüme geliştirilmesi |

Malzeme tarifleri tarif kitabında ayrı ayrı gösterilir. Her tarif aranabilir ve üretim alanına otomatik olarak yerleştirilebilir.

---

# ⚒️ Üretim Sistemi

Zahility'nin kar topu ilerleyişi üç ana aşamadan oluşur.

## 1. İlk Üretim

Bir özel kar topunun ilk üretiminde genel olarak şunlar kullanılır:

- Snowball
- Özel Kar Topu Şablonu
- Kar topunun özelliğine uygun bir katalizör

İlk üretim sonucunda:

```text
4 özel kar topu
```

elde edilir.

## 2. Çoğaltma

Daha önce üretilmiş normal bir özel kar topu, destekleyici malzemelerle birlikte kullanılarak çoğaltılabilir.

Çoğaltma sonucunda:

```text
8 özel kar topu
```

elde edilir.

Bu sistem, oyuncunun her seferinde ilk üretimde kullanılan pahalı şablonu hazırlamasını gerektirmez.

## 3. Sonsuz Geliştirme

Normal bir özel kar topunu Sonsuz sürüme yükseltmek için genel olarak şunlar gerekir:

- İlgili normal özel kar topu
- Sonsuzluk Çekirdeği
- Netherite Block

Üretim sonucunda:

```text
1 Sonsuz özel kar topu
```

elde edilir.

Sonsuz sürüm kullanıldığında tüketilmez ve normal sürümden daha güçlüdür.

## Tarif sayısı

Zahility Tezgâhında mevcut olarak:

| Tarif türü | Tarif sayısı |
|---|---:|
| Malzeme | 2 |
| İlk üretim | 6 |
| Çoğaltma | 6 |
| Sonsuz geliştirme | 6 |
| **Toplam** | **20** |

Özel tariflerin hiçbiri normal Crafting Table içerisinde çalışmaz.

---

# 🌐 Dil Desteği

Zahility şu anda iki dili desteklemektedir:

| Dil | Kod |
|---|---|
| Türkçe | `tr_tr` |
| İngilizce | `en_us` |

Eşya adları, menü yazıları, tarif kitabı kategorileri ve oyuncuya gösterilen işlem mesajları Minecraft'ın çeviri sistemi üzerinden yönetilir.

---

# 📥 Kurulum

> Henüz kararlı bir sürüm yayımlanmadığı için bu bölüm geliştirme sürümünü kaynak koddan çalıştırmak isteyenler içindir.

## Gereksinimler

- Minecraft Java Edition 1.21.1
- NeoForge 21.1.244 veya daha yeni uyumlu bir 21.1 sürümü
- Java 21

## Kaynak kodu indirme

```powershell
git clone https://github.com/zahidkaya1/Zahility.git
cd Zahility
```

## Java kodunu derleme

```powershell
.\gradlew.bat compileJava
```

## Kaynak dosyalarını işleme

```powershell
.\gradlew.bat processResources
```

## Geliştirme istemcisini çalıştırma

```powershell
.\gradlew.bat runClient
```

## Temiz doğrulama

```powershell
.\gradlew.bat clean processResources compileJava --console=plain
```

---

# 🧪 Geliştirme Durumu

Zahility şu anda aktif olarak geliştirilmektedir.

## Tamamlanan sistemler

- [x] Topraklaştırıcı Kar Topu
- [x] Düzleştirici Kar Topu
- [x] Sünger Kar Topu
- [x] Dondurucu Kar Topu
- [x] Yeşertici Kar Topu
- [x] Kovucu Kar Topu
- [x] Normal ve Sonsuz kar topu sürümleri
- [x] Zahility Tezgâhı
- [x] Özel 3×3 tarif sistemi
- [x] Vanilla tarzı tarif kitabı
- [x] İlk üretim tarifleri
- [x] Çoğaltma tarifleri
- [x] Sonsuz geliştirme tarifleri
- [x] Türkçe ve İngilizce çeviriler
- [x] Kar topu görsellerinin standartlaştırılması
- [x] İşlem mesajlarının iyileştirilmesi

## Planlanan geliştirmeler

- [ ] Tarif ve maliyet dengelemesi
- [ ] Eşya açıklamaları ve gelişmiş tooltip sistemi
- [ ] Ses ve parçacık efektlerinin cilalanması
- [ ] Yeni yardımcı eşyalar
- [ ] Yeni Zahility Tezgâhı tarifleri
- [ ] Yapılandırma seçenekleri
- [ ] Çok oyunculu testler
- [ ] İlk kararlı sürüm
- [ ] Dağıtıma hazır mod paketi

---

# 🗂️ Teknik Bilgiler

| Bilgi | Değer |
|---|---|
| Mod adı | Zahility |
| Mod kimliği | `zahility` |
| Java paketi | `me.zahidkaya.zahility` |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.244 |
| Java | 21 |
| Mevcut sürüm | 0.1.0 |
| Ana dal | `main` |
| Geliştirici | Mehmet Zahid Kaya |
| Depo | [github.com/zahidkaya1/Zahility](https://github.com/zahidkaya1/Zahility) |

---

# 🧭 Gelecek Planları

Zahility ilerleyen sürümlerde yalnızca özel kar toplarından oluşan bir mod olmayacaktır.

Planlanan genel geliştirme alanları:

- Yeni yardımcı araçlar
- Yeni işlevsel bloklar
- Yeni üretim malzemeleri
- Tarım ve doğa yardımcıları
- Arazi düzenleme araçları
- Envanter ve üretim kolaylıkları
- Keşif yardımcıları
- Yapı inşa etmeyi kolaylaştıran dengeli sistemler
- Sunucu ve çok oyunculu kullanım iyileştirmeleri
- Yapılandırılabilir etki alanları ve davranışlar

Yeni özellikler eklenirken Vanilla+ tasarım anlayışı ve Survival dengesi korunacaktır.

---

# 📜 Lisans

Copyright © Mehmet Zahid Kaya.

Bu proje **All Rights Reserved** lisansı altında yayımlanmaktadır.

Projenin kaynak kodunu veya varlıklarını, geliştiricinin açık izni olmadan kopyalama, değiştirme, yeniden dağıtma veya başka bir projede yayımlama izni verilmemektedir.

---

<div align="center">

Geliştirici: **Mehmet Zahid Kaya**

[GitHub deposuna dön](https://github.com/zahidkaya1/Zahility)

</div>