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

Ya, menurut saya implementasi workflow GitHub yang ada saat ini sudah sangat memenuhi definisi Continuous Integration (CI) maupun Continuous Deployment (CD). Dari sisi Continuous Integration, setiap kali terdapat perubahan kode yang di push atau di merge, GitHub Actions secara otomatis akan menjalankan proses build, mengeksekusi unit test suite (beserta laporan coverage dari JaCoCo), dan melakukan analisis kualitas kode menggunakan SonarCloud. Proses otomatis ini memastikan bahwa kode baru yang diintegrasikan aman, tidak merusak fitur yang sudah ada, dan memenuhi standar kualitas sebelum benar-benar digabungkan ke repositori utama. Sementara itu, dari sisi Continuous Deployment, alur kerja ini juga sudah berjalan dengan baik karena integrasi dengan Koyeb. Setiap kali ada kode yang berhasil di merge ke branch main, Koyeb akan secara otomatis mendeteksi perubahan tersebut (menggunakan pull-based approach), melakukan build pada Docker image, lalu mengdeploy aplikasi langsung ke server production.