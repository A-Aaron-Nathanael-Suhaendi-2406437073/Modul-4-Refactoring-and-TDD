Nama: Aaron Nathanael Suhaendi
NPM: 2406437073
Kelas: Adpro - A


MODULE 1

Reflection 1

Clean Code Principles and Secure Coding

Setelah mengimplementasikan fitur Edit dan Delete, saya merasa penerapan prinsip Clean Code dan Secure Coding sangat membantu dalam keterbacaan kode. Beberapa hal yang sudah saya terapkan antara lain sebagai berikut:

1. Meaningful Names
   Saya berusaha memberi nama variabel dan method yang deskriptif. Contohnya delete(String productId) dan findById(String productId) di Service dan Repository. Hal ini membuat kode menjadi lebih mudah dipahami. Orang lain yang membaca kode tersebut atau bahkan saya sendiri di masa depan, dapat langsung mengerti fungsinya tanpa harus melihat detail implementasinya.

2. Single Responsibility Principle (SRP)
   Saya tetap menjaga pemisahan tugas yang jelas dengan pola MVC. Controller hanya bertugas mengatur alur request, Service mengatur logika bisnis seperti memanggil repository, dan Repository fokus pada manipulasi data di dalam list. Dengan cara ini, tidak ada logika bisnis yang tercampur ke dalam Controller.

3. Secure Coding (UUID)
   Awalnya saya tidak terlalu memikirkan soal ID, namun kemudian saya menyadari bahwa penggunaan ID berurutan seperti 1, 2, 3, dan seterusnya tidak aman karena mudah ditebak dan berpotensi menimbulkan serangan ID Enumeration. Oleh karena itu, saya menerapkan penggunaan UUID secara acak saat produk dibuat. Dengan cara ini, URL untuk fitur edit dan delete menjadi unik dan sulit diprediksi oleh pihak yang tidak bertanggung jawab.


Mistakes and Evaluation

Jujur masih terdapat beberapa kekurangan dalam kode yang saya buat, sekaligus pelajaran yang bisa saya ambil seperti:

1. Validasi Input
   Saat ini, logika validasi yang diterapkan masih sangat minim. User masih bisa memasukkan nama produk yang kosong atau quantity bernilai negatif. Ke depannya, saya perlu menambahkan validasi baik di level Model maupun Controller agar data yang masuk benar2 valid.

2. Konsistensi Path URL
   Saya sempat mengalami kebingungan dalam penggunaan path relatif seperti ../list dibandingkan dengan path absolut seperti /product/list saat melakukan redirect. Meskipun penggunaan ../list tetap berfungsi, pendekatan ini cukup rentan menimbulkan error jika struktur URL berubah. Oleh karena itu, sebaiknya saya konsisten menggunakan absolute path agar navigasi aplikasi menjadi tidak membingungkan.


Reflection 2

Nomor 1
Setelah menulis unit test, jujur rasanya campur aduk. Awalnya terasa agak ribet karena harus memikirkan skenario tes dan menulis kode tambahan selain fitur utamanya. Tapi setelah melihat semua tes "passed" (hijau), saya merasa jauh lebih percaya diri dengan kode yang saya tulis. Rasanya lebih aman kalau nanti mau refactor atau nambah fitur, karena kalau ada yang rusak pasti ketauan.

Mengenai jumlah unit test dalam satu class, menurut saya tidak ada angka yang pasti yang harus dicapai. Jumlahnya tergantung seberapa kompleks logic di dalam class tersebut. Yang penting adalah memastikan semua behavior penting sudah terverifikasi, termasuk skenario positif (input benar) dan skenario negatif (input salah).

Untuk memastikan unit test sudah cukup, kita bisa menggunakan bantuan Code Coverage. Ini adalah metrik yang menunjukkan seberapa banyak baris kode kita yang dieksekusi saat tes berjalan. Semakin tinggi persentasenya, semakin banyak kode yang tersentuh oleh tes. Namun, 100% Code Coverage bukan jaminan kode bebas dari bug. Coverage hanya memberi tahu bahwa baris kode tersebut pernah dijalankan, tapi tidak menjamin logikanya benar atau semua kemungkinan input (edge cases) sudah ditangani. Bisa saja barisnya tereksekusi, tapi hasil outputnya salah dan assert nya kurang lengkap.

Nomor 2
Jika saya membuat functional test suite baru untuk memverifikasi jumlah item, lalu saya hanya melakukan copy-paste setup procedures dan instance variables dari CreateProductFunctionalTest.java, menurut saya itu akan menurunkan kualitas kode (code cleanliness).

Masalah utamanya adalah Code Duplication. Ini akan melanggar prinsip DRY (Don't Repeat Yourself). Setup prosedur seperti konfigurasi port, inisialisasi driver Selenium, dan base URL adalah kode yang repetitif.

Kenapa ini masalah? Jika suatu saat saya perlu mengubah cara inisialisasi driver atau mengganti port konfigurasi, saya harus mengubahnya secara manual di semua file test suite (CreateProductFunctionalTest, functional test baru, dll). Ini membuat kode susah untuk di maintenance dan rawan error kalau ada satu file yang lupa buat diubah.

Saran Perbaikan: Solusinya adalah membuat sebuah Base Test Class (misalnya BaseFunctionalTest.java). Di dalam class ini, kita simpan semua setup umum seperti @LocalServerPort, inisialisasi driver, dan method @BeforeEach. Nanti, CreateProductFunctionalTest dan functional test suite yang baru tinggal melakukan extends ke class base tersebut. Dengan begitu, kode setup hanya ditulis satu kali dan bisa dipakai ulang (reusable), sehingga kode jadi lebih clean.




MODULE 2

Nomor 1 : List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them


Selama mengerjakan exercise ini, saya menemukan dan memperbaiki 4 code quality issues utama yang dideteksi oleh SonarCloud:

1. Group dependencies by their destination (build.gradle.kts)

Masalah: Deklarasi dependencies di dalam file Gradle berantakan dan tidak dikelompokkan berdasarkan tipenya (seperti implementation, testImplementation, compileOnly, dll).

Strategi: Saya menyusun ulang baris-baris dependencies tersebut agar mengelompok sesuai dengan tujuannya. Strategi ini sangat penting untuk maintainability, sehingga developer lain bisa dengan cepat melihat library mana yang dipakai untuk production dan mana yang khusus untuk testing.

2. Remove this field injection and use constructor injection instead (ProductController.java)

Masalah: Saya menggunakan @Autowired langsung pada field ProductService (Field Injection). SonarCloud menandai ini sebagai Major Code Smell terkait Reliability dan Maintainability.

Strategi: Saya menghapus anotasi @Autowired pada field dan membuat Constructor yang menerima parameter ProductService (Constructor Injection). Ini adalah best practice dalam Spring Boot karena membuat dependencies menjadi eksplisit, dan juga mencegah objek dibuat dalam state yang tidak valid (tanpa dependency), dan yang paling penting adlah membuat kode jauh lebih mudah untuk di test.

3. Add at least one assertion to this test case (EshopApplicationTests.java)

Masalah: Terdapat sebuah method test yang kosong atau tidak memiliki assert sama sekali.

Strategi: Saya menambahkan assertion sederhana di dalam method tersebut (misalnya mengecek apakah context berhasil di load atau menggunakan assert yang relevan). Strategi ini memastikan bahwa tes tersebut benar-benar memvalidasi sesuatu dan berguna sebagai safety net aplikasi.

4. Remove this unused import 'java.util.Iterator' (ProductServiceImplTest.java)

Masalah: Terdapat import class di bagian atas file yang sebenarnya sama sekali tidak digunakan di dalam baris kode di bawahnya.

Strategi: Saya menghapus baris import tersebut. Meskipun terlihatny sepele, membersihkan kode dari import yang tidak terpakai adalah praktik Clean Code yang baik untuk mengurangi clutter dan menghindari kebingungan saat sedang membaca kode.



Nomor 2 : Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Ya, menurut saya implementasi workflow GitHub yang ada saat ini sudah sangat memenuhi definisi Continuous Integration (CI) maupun Continuous Deployment (CD). Dari sisi Continuous Integration, setiap kali ada perubahan kode yang di push atau di merge, GitHub Actions secara otomatis akan menjalankan proses build, mengeksekusi unit test suite (beserta laporan coverage dari JaCoCo), dan melakukan analisis kualitas kode menggunakan SonarCloud. Proses otomatis ini memastikan bahwa kode baru yang diintegrasikan aman, tidak merusak fitur yang sudah ada, dan memenuhi standar kualitas sebelum benar2 digabungkan ke repositori utama. Sementara itu, dari sisi Continuous Deployment, alur kerjanya berjalan menggunakan pendekatan push-based. Saya telah membuat workflow tambahan (deploy.yml) yang memanfaatkan GitHub Actions untuk secara otomatis memicu proses deployment ke Koyeb setiap kali ada kode yang berhasil di merge ke branch main. Dengan cara ini, GitHub secara aktif mengirimkan perintah ke Koyeb untuk membuild dan mengdeploy aplikasi ke server production tanpa intervensi manual, ini memastikan fitur terbaru dapat segera digunakan oleh pengguna.




MODULE 3


Nomor 1 :  Explain what principles you apply to your project!

Pada proyek ini, saya telah menerapkan kelima prinsip SOLID (4 yang saya apply, yaitu SRP, OCP, LSP, DIP):

Single Responsibility Principle (SRP): Saya memisahkan ProductController dan CarController ke dalam 2 file Java yang berbeda. Sekarang, setiap controller hanya memiliki satu alasan untuk berubah (satu fokus tanggung jawab), yaitu mengurus rutenya masing-masing (satu ngurusin rute Product, satu ngurusin rute Car).

Open-Closed Principle (OCP): Selain penggunaan interface CarService yang memungkinkan sistem terbuka terhadap penambahan fitur, saya juga menyempurnakan OCP dengan mengubah CarRepository menjadi sebuah interface dan memisahkan implementasi konkritnya ke CarRepositoryImpl. Jika ke depannya aplikasi beralih menggunakan database asli (seperti PostgreSQL), saya cukup membuat class baru yang mengimplementasikan interface tersebut tanpa perlu memodifikasi kode yang sudah ada.

Liskov Substitution Principle (LSP): Saya menghapus hierarki inheritance (extends ProductController) pada CarController. Sebuah mobil (Car) bukanlah anak dari entitas produk (Product) secara konseptual di arsitektur ini. Memisahkan keduanya memastikan perilaku program tetap konsisten.

Interface Segregation Principle (ISP): Saya sudah mengimplementasikan ini di code before-solid, Interface CarService dibuat spesifik dan berukuran kecil (hanya berisi metode CRUD khusus car). Class yang mengimplementasikan interface ini juga tidak dipaksa untuk override fungsi-fungsi yang tidak berhubungan.

Dependency Inversion Principle (DIP): Saya mengubah cara injeksi dependensi dari Field Injection (@Autowired di atas variabel) menjadi Constructor Injection. Selain itu, komponen tingkat tinggi sekarang bergantung pada abstraksi, bukan kelas konkrit: CarController bergantung pada interface CarService, dan CarServiceImpl bergantung pada interface CarRepository.



Nomor 2 : Explain the advantages of applying SOLID principles to your project with examples.

Keuntungan utama menerapkan SOLID adalah membuat kode menjadi lebih maintainable, skalabel, dan mudah diuji (testable).

Contoh SRP: Jika terdapat bug pada fitur routing mobil, saya tahu persis saya hanya perlu memeriksa file CarController.java tanpa takut merusak fitur atau mengganggu code milik Product. File code juga menjadi lebih pendek dan mudah dibaca.

Contoh DIP dan OCP: Dengan bergantung pada abstraksi (interface CarService dan CarRepository) serta menggunakan Constructor Injection, kode saya menjadi sangat mudah untuk dilakukan Unit Testing. Saya bisa dengan mudah memasukkan objek tiruan (mock data) lewat constructor tanpa harus menjalankan server secara penuh. Jika ke depannya proyek ini beralih dari penyimpanan memori (ArrayList) ke basis data nyata, saya hanya perlu membuat class CarRepositoryPostgresImpl.java tanpa perlu mengubah satu baris pun di CarServiceImpl.java.


Nomor 3 : Explain the disadvantages of not applying SOLID principles to your project with examples.

Tidak menerapkan prinsip SOLID akan membuat kode menjadi kaku (rigid), rapuh (fragile), dan sangat sulit dikelola seiring membesarnya ukuran proyek (biasanya disebut Spaghetti Code).

Contoh (Pelanggaran SRP): Saat CarController masih tergabung di dalam file ProductController.java (sebelum refactoring), file tersebut menanggung terlalu banyak beban. Jika proyek membesar dan memiliki fitur User, Payment, dll yang juga ditumpuk di satu file, file tersebut bisa mencapai ribuan baris kode yang akan sangat menyulitkan developer saat mencari letak error.

Contoh (Pelanggaran DIP dan OCP): Saat menggunakan Field Injection (@Autowired private CarServiceImpl carservice;), Controller bergantung pada satu class konkrit yang sangat spesifik. Begitu juga jika Service bergantung langsung pada CarRepository versi ArrayList. Ini membuat kode sangat kaku. Jika nanti tiba-tiba kita diminta mengganti algoritma penyimpanan atau layanan, saya terpaksa harus membongkar ulang dan menghapus kode lama di Controller atau Service, ini  bisa berisiko tinggi memunculkan error baru di tempat lain.



MODULE 4


Reflection 1

Nomor 1 : Reflection on TDD usefulness based on Percival's (2017) objectives

Menjawab evaluasi obyektif pengujian dari Percival (2017), secara keseluruhan alur TDD ini sudah terasa cukup berguna bagi saya, meskipun ada beberapa penyesuaian yang harus saya rasakan di awal. Berikut adalah penjabaran dari ketiga obyektif tersebut:

Correctness (Kebenaran): TDD memaksa saya untuk memikirkan semua skenario dan edge cases secara menyeluruh sejak awal. Contohnya, saat membuat OrderTest, saya langsung dipaksa membuat tes untuk status pesanan yang invalid atau list produk yang kosong. Hal ini memberi saya kepastian fungsional dari sudut pandang pengguna bahwa aplikasinya benar-benar bekerja sesuai ekspektasi.

Maintainability (Kemudahan Pemeliharaan): Ini adalah manfaat TDD yang paling terasa. Tes yang sudah saya buat di fase awal memberikan rasa percaya diri yang tinggi untuk melakukan refactoring tanpa rasa takut akan merusak kode yang sudah ada. Ketika saya harus mengubah kode status dari tulisan teks biasa menjadi Enum OrderStatus, tes yang sudah ada langsung memastikan bahwa perubahan yang saya lakukan tidak merusak desain program.

Productive Workflow (Alur Kerja Produktif): Di obyektif ini, sejujurnya saya merasa masih butuh adaptasi. Pada awalnya, keharusan menulis kode tes yang dipastikan gagal (fase RED) terlebih dahulu terasa memperlambat alur kerja, sehingga feedback cycles terasa sedikit lambat di awal. Ke depannya, hal yang perlu saya lakukan adalah membiasakan ritme TDD ini agar saya tidak merasa terlalu lama menunggu dalam menulis tes, dan pada akhirnya bisa mendapatkan peringatan dini terkait bug dengan lebih mulus dan produktif.



Nomor 2 : Reflection on F.I.R.S.T. principles implementation

Menurut saya, unit test yang sudah saya kerjakan di sesi tutorial ini sebagian besar sudah berhasil mematuhi prinsip F.I.R.S.T:

Fast (Cepat): Tes berjalan sangat cepat (hanya dalam hitungan milidetik) karena saya tidak perlu menyalakan database asli, melainkan menggunakan memori data lokal (ArrayList) dan memanfaatkan fitur mocking (Mockito) pada OrderServiceImpl.

Independent (Mandiri): Setiap test case berdiri sendiri dan tidak saling memengaruhi. Penggunaan anotasi @BeforeEach sangat membantu untuk me-reset dan menyiapkan objek dummy sebelum setiap tes dijalankan, sehingga tidak ada "sampah data" yang bocor ke tes selanjutnya.

Repeatable (Dapat Diulang): Tes ini bisa dieksekusi secara berulang dengan hasil yang konsisten, baik di lingkungan lokal IntelliJ milik saya maupun ketika dijalankan di dalam pipeline otomatis seperti GitHub Actions.

Self-Validating (Memvalidasi Sendiri): Seluruh tes sudah menghasilkan output mutlak (berhasil atau gagal) tanpa perlu dievaluasi secara manual oleh mata manusia. Penggunaan fungsi seperti assertEquals, assertNull, dan assertThrows membuat IDE langsung menampilkan indikator hijau atau merah dengan jelas.

Timely (Tepat Waktu): Karena tugas ini mengharuskan saya mempraktikkan TDD, maka prinsip ini secara otomatis sudah terpenuhi. Unit tests dibuat secara tepat waktu persis sebelum kode produksinya (Order, OrderRepository, OrderService) ditulis.

Hal yang perlu saya lakukan ke depannya:

Meskipun prinsip dasar sudah tercapai, ke depannya saya perlu lebih memperhatikan seberapa rapi penulisan kode tes tersebut (clean test code). Terkadang setup data dummy nya masih cukup repetitif dan panjang. Saya harus membiasakan diri untuk memecah tes kompleks agar benar-benar hanya berfokus menguji satu single behavior saja, sehingga prinsip Independent dan Fast bisa dipertahankan ketika sistemnya menjadi lebih besar.



Reflection 2 (BONUS 2)
Di sini, saya memeriksa code milik teman kelompok saya Jovian Felix Rustan (2406360016)


1. What do you think about your partner's code? Are there any aspects that are still lacking?

Secara fungsional, kode yang disusun oleh rekan saya sudah berjalan dengan baik dan memenuhi requirement fitur yang diminta. Struktur class dan penamaan variabelnya juga cukup deskriptif sehingga alur logika mudah dipahami. Namun, dari aspek clean code dan maintainability, masih terdapat beberapa kekurangan. Hal yang paling menonjol adalah adanya ketergantungan antar komponen yang masih menggunakan Field Injection, adanya redundansi logika pada Controller, serta beberapa unused code dan deklarasi exception yang tidak perlu di dalam kelas pengujian. Aspek-aspek ini jika dibiarkan dapat mempersulit proses unit testing dan pengembangan di masa depan.

2. What did you do to contribute to your partner's code?

Kontribusi saya berfokus pada proses Quality Assurance melalui Code Review dan pelaksanaan refaktorisasi. Saya melakukan identifikasi terhadap berbagai code smell yang terdeteksi secara manual maupun melalui standar SonarCloud. Setelah itu, saya memberikan saran perbaikan dan melakukan implementasi refaktorisasi pada beberapa class utama seperti OrderController, PaymentController, serta beberapa class Service dan Test untuk memastikan kode tersebut memenuhi standar best practices dalam pengembangan Spring Boot.

3. What code smells did you find on your partner's code?

Beberapa code smell yang saya temukan meliputi:

Dependency Injection Issue: Penggunaan @Autowired langsung pada field (Field Injection) di kelas Service dan Controller.

Code Duplication (DRY Principle): Terdapat logika pengisian data model yang serupa di beberapa method pada OrderController.

Unused Code: Adanya variabel privat dan deklarasi throws Exception pada Functional Test yang tidak pernah digunakan.

Magic Strings: Penggunaan literal string yang berulang untuk nama view (seperti "orderHistory").

Redundant Assertions: Pemanggilan lebih dari satu metode di dalam lambda assertThrows yang berisiko menimbulkan false positive.



4. What refactoring steps did you suggest and execute to fix those smells?

Langkah-langkah refaktorisasi yang saya sarankan dan eksekusi adalah:

Mengubah Injection Style: Mengganti Field Injection menjadi Constructor Injection menggunakan keyword final untuk menjamin immutability dan mempermudah pengujian manual.

Extract Method: Mengambil logika yang berulang pada OrderController dan memindahkannya ke dalam satu private helper method (populateHistoryModel).

Konstanta untuk Literal: Mendefinisikan konstanta static final String untuk menggantikan literal string yang muncul berulang kali.

Clean Up: Menghapus variabel yang tidak terpakai dan menyederhanakan deklarasi metode pada kelas pengujian.

Refactoring Unit Test: Mengonsolidasikan tes yang serupa menggunakan @ParameterizedTest dan memisahkan kode persiapan dari dalam blok assertThrows agar pengujian lebih akurat.
