## Modul Payment 💰
Deskripsi:
Sistem pembayaran untuk transaksi pendaftaran kursus. Transaksi memiliki status PENDING, PAID, FAILED. Ketika Student mendaftar ke kursus yang memiliki harga tertentu, maka sistem akan secara otomatis membuat sebuah transaksi yang memiliki status PENDING.
Student dapat melakukan pembayaran kursus melalui metode pembayaran yang tersedia. (C)

Setiap transaksi minimal memiliki beberapa data yang dapat ditampilkan pengguna ketika melakukan pembayaran:
- ID transaksi
- Nama kursus yang dibeli
- Nama tutor yang membuat kursus
- Harga kursus

Metode pembayaran memiliki dua metode. Masing-masing metode memiliki data yang berbeda:
1. Transfer Bank

Sistem akan menampilkan daftar nama Bank dan nomor rekening yang tersedia. Setelah membayar, pengguna harus melakukan konfirmasi ‘Saya sudah transfer’.
2. Kartu Kredit

Pengguna memasukkan nomor rekening dan Card Verification Code (CVC).
Setelah melakukan pembayaran, transaksi baru selesai setelah Admin mengubah status transaksi menjadi PAID atau FAILED melalui fitur Staff Dashboard.
- Student dapat melihat riwayat dan status transaksi pembayaran kursus (R)
- Student dapat membatalkan transaksi atau mengajukan refund (C)

### Design Pattern untuk Payment
Untuk saat ini, *design pattern* yang cocok untuk modul Payment adalah sebagai berikut:

#### 1. **Command Pattern**


**Kenapa:**  
Untuk meng-handle aksi pengguna seperti "konfirmasi pembayaran", 
"pembatalan", atau "ajukan refund", Command pattern bisa membantu agar setiap aksi menjadi 
kelas terpisah dengan logikanya sendiri.


### 2. **State Pattern**
Transaksi bisa memiliki status `PENDING`, `PAID`, atau `FAILED`. 
Dibandingkan dengan menggunakan if-else berulang, penggunaan **State pattern** dapat membantu mendefinisikan
setiap status sebagai sebuah state dengan behavior masing-masing.

### 3. **Strategy Pattern**
**Kenapa:**  
Untuk menangani **berbagai metode pembayaran** (Transfer Bank vs Kartu Kredit), 
Strategy pattern cocok banget. 
Setiap metode pembayaran bisa punya class-nya sendiri dengan behavior masing-masing.

Lalu implementasi untuk:
- `BankTransferPaymentStrategy`
- `CreditCardPaymentStrategy`

### 4. **Factory Pattern**
**Kenapa:**  
Untuk membuat instance dari strategi pembayaran sesuai metode yang dipilih oleh user (`BANK_TRANSFER`, `CREDIT_CARD`), Factory pattern bisa bantu supaya tidak hardcoded.

### 5. **Repository Pattern (Spring Data JPA)**
**Kenapa:**  
Standar Spring Boot buat persistence, jadi semua transaksi, user, kursus bisa disimpan dan diambil lewat repository.


