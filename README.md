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
Ada beberapa aksi berbeda yang dapat dilakukan pengguna, seperti 
membayar course, membatalkan transaksi, atau mengajukan refund.
Menggunakan **Command pattern** bisa memisahkan tiap aksi menjadi kelas masing-masing
dengan logikanya sendiri.

### 2. **State Pattern**
Transaksi bisa memiliki status `PENDING`, `PAID`, atau `FAILED`. 
Dibandingkan dengan menggunakan if-else berulang, penggunaan **State pattern** dapat membantu mendefinisikan
setiap status sebagai sebuah state dengan behavior masing-masing.

### 3. **Strategy Pattern**
**Strategy pattern** digunakan untuk menangani dua metode pembayaran yang berbeda,
yakni Transfer Bank dan Kartu Kredit.
Dengan pattern ini, setiap metode pembayaran didefinisikan di classnya masing-masing
dengan perilaku yang berbeda.

### 4. **Factory Pattern**
**Factory Pattern** bisa membantu membuat instance strategi pembayaran 
sesuai metode yang dipilih oleh pengguna agar tidak terlalu hard-coded.

