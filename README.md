# NhomFuHo2005
Chủ đề của bài tập lớn là phát triển trò chơi Arkanoid, một game kinh điển thuộc thể loại phá gạch. Nhiệm vụ của người chơi là điều khiển thanh đỡ (Paddle) để giữ bóng không rơi xuống và phá vỡ toàn bộ gạch trên màn hình.
<div align="center">

# 🎮 OOP Project — Java Brick Breaker Game
### 🧱 A modern object-oriented remake of the classic Arkanoid game

---



</div>

---

## 📘 Giới thiệu  

Dự án **OOP-Project** là một game **phá gạch (Breakout/Arkanoid)** được phát triển hoàn toàn bằng **Java**, sử dụng kiến trúc **lập trình hướng đối tượng (OOP)**.  
Người chơi điều khiển thanh **Paddle** để đập bóng phá gạch, thu thập vật phẩm hỗ trợ và vượt qua nhiều màn chơi khác nhau.

🎯 **Mục tiêu:**  
- Ứng dụng các nguyên lý OOP trong phát triển game.
- Xây dựng cấu trúc mã rõ ràng, dễ mở rộng và bảo trì.
- Tạo trải nghiệm chơi mượt mà, vui nhộn và có chiều sâu.

---

## ✨ Tính năng nổi bật  

### 🕹️ Gameplay
- Nhiều **màn chơi** với độ khó tăng dần.  
- Hệ thống **điểm số và mạng sống** (3 mạng mặc định).  
- **Vật lý bóng** với xử lý va chạm chính xác.  
- **Power-ups đa dạng**, giúp tăng sức mạnh paddle hoặc bóng.  


### 💥 Power-ups
| Biểu tượng | Tên                | Hiệu ứng chính                         |
|-------------|-------------------|----------------------------------------|
| 🔴| **ExpandPaddle**     | Mở rộng kích thước Paddle              |
| 🔵| **FastBall**          | Tăng tốc độ di chuyển của bóng         |
| 🟢 | **LaserPaddle**       | Cho phép Paddle bắn laser              |

### 🎨 Giao diện & Âm thanh
- Menu chính, HUD hiển thị điểm và mạng sống.
- Hiệu ứng âm thanh khi bóng nảy, phá gạch, nhận vật phẩm.
- Nhạc nền và font chữ tùy chỉnh mang phong cách arcade.

---

## 🧩 Cấu trúc dự án  
```bash
project-root/
│
├── src/
│   └── game/
│       ├── core/
│       │   ├── App.java                  # Chạy game (main frame)
│       │   ├── GameManager.java          # Quản lý vòng lặp game, đối tượng
│       │   ├── LevelLoader.java          # Nạp dữ liệu màn chơi
│       │   ├── KeyPress.java             # Xử lý bàn phím
│       │   ├── Music.java                # Xử lý âm thanh nền
│       │   └── EndGame.java              # Màn hình kết thúc
│       │
│       ├── entities/                     # Các thực thể trong game
│       │   ├── GameObject.java
│       │   ├── MovableObject.java
│       │   ├── Ball.java
│       │   ├── Paddle.java
│       │   ├── Brick.java
│       │   ├── NormalBrick.java
│       │   ├── StrongBrick.java
│       │   ├── PowerUp.java
│       │   ├── ExpandPaddlePowerUp.java
│       │   ├── FastBallPowerUp.java
│       │   ├── LaserPaddlePowerUp.java
│       │   └── Bullet.java
│       │
│       ├── levels/
│       │   └── Level.json                # 10 màn (dữ liệu màn chơi)
│       │
│       ├── ui/                           # Giao diện người dùng
│       │
│       └── utils/                        # Tiện ích, constants, helper
│
├── assets/                               # Tài nguyên game
│   ├── images/
│   ├── sounds/
│   └── fonts/
│
├── lib/
│   └── gson-2.11.0.jar
│
├── docs/
│   ├── UML diagrams.pdf
│   └── folderMap.txt
│
├── build/
│
├── config.json
└── README.md
```

## 🧰 Yêu cầu hệ thống  

| Thành phần | Yêu cầu tối thiểu |
|-------------|------------------|
| ☕ **Java** | JDK 17 hoặc mới hơn |
| 💻 **RAM** | 512 MB |
| 🧩 **Màn hình** | 800×600 trở lên |
| 🎧 **Âm thanh** | Hỗ trợ Java Sound API |
| 🖥️ **Hệ điều hành** | Windows / macOS / Linux |

---

## ⚙️ Cài đặt & Chạy game  

### 1️⃣ Clone repository  
```bash
git clone https://github.com/PhongGoldFist2005-glitch/NhomFuHo2005.git
cd NhomFuHo2005
```
### 2️⃣ Mở trong IDE
IntelliJ IDEA: mở project, chạy App.java.
Eclipse: File → Import → Existing Java Project.

### 2️⃣ Chạy game
javac src/game/core/App.java
java -cp src game.core.App

🕹️ Cách chơi
Hành động	Phím điều khiển
Di chuyển trái	⬅️ hoặc A
Di chuyển phải	➡️ hoặc D
Bắt đầu / Bắn bóng	Space
Tạm dừng	P
Thoát	ESC

🎯 Mục tiêu:
Phá toàn bộ gạch để qua màn, hứng các vật phẩm rơi xuống để được buff.
Khi hết bóng (mất 3 mạng) → Game Over!

🧠 Kiến trúc & Mẫu thiết kế

🧩 Design Patterns

Singleton → Cho GameManager để quản lý các tác vụ chính trong game.

Strategy → Tùy biến hành vi va chạm và vật thể.

State → Quản lý các trạng thái: Menu, Playing, Paused, EndGame.

🔄 Game Loop (60 FPS)

Xử lý input người chơi.

Cập nhật trạng thái các đối tượng.

Kiểm tra va chạm & cập nhật điểm.

Vẽ lại toàn bộ khung hình (Canvas/Graphics2D).

| Vai trò        | Tên                     | GitHub                                             |
| -------------- | -------------------     | -------------------------------------------------- |
| 👑 Trưởng nhóm | **Nguyễn Thế Phong**    | [@PhongGoldFist2005-glitch](https://github.com/PhongGoldFist2005-glitch)       |
| 🧩 Thành viên  | **Trịnh Quang Sáng**    | [@23020867-boop](https://github.com/23020867-boop)           |
| 🧱 Thành viên  | **Phùng Khắc Tâm**     | [@23020871-Tamphung](https://github.com/23020871-Tamphung) |

🪪 License

Dự án phát hành theo MIT License — bạn có thể sử dụng, chỉnh sửa và phân phối tự do.

Xem chi tiết trong file LICENSE.

💖 Lời cảm ơn

Cảm ơn đội ngũ NhomFuHo2005 đã phát triển dự án này trong khuôn khổ môn học Lập trình Hướng Đối Tượng (OOP).
Nguồn cảm hứng đến từ game cổ điển Arkanoid (Taito Corporation).

Nếu bạn thích dự án này, hãy ⭐ star repo trên GitHub nhé!

---

## 👥 Đánh giá & Phân công công việc

Dưới đây là chi tiết phần công việc, mức độ đóng góp và vai trò của từng thành viên trong nhóm trong quá trình phát triển dự án **OOP Project – Java Brick Breaker Game**.

---

### 🧩 1. Phong — **Lập trình viên chính / Trưởng nhóm**

**Tổng đóng góp: ~40%**

Phong đảm nhiệm vai trò **lập trình viên chính** và **thiết kế kiến trúc tổng thể** cho dự án.  
Anh ấy chịu trách nhiệm điều phối công việc giữa các thành viên, thiết kế giao diện game, xây dựng vòng lặp game, và đảm bảo luồng logic hoạt động xuyên suốt.  

**Chi tiết đóng góp:**
- **`App.java` (100%)** – Xây dựng giao diện ban đầu của game, bao gồm các chức năng:  
  > Bắt đầu game, thoát game, bật/tắt nhạc, hiển thị màn hình chính.  
- **`EndGame.java` (100%)** – Thiết kế logic kết thúc game, bao gồm quy trình **restart** và quay lại menu chính.  
- **`GameManager.java` (33.33%)** – Phát triển **vòng lặp game (game loop)**, quản lý **FPS**, logic **reset/restart**, và điều phối luồng hoạt động chính.  
- **`KeyPress.java` (100%)** – Xây dựng hệ thống xử lý bàn phím: di chuyển paddle, bắn bóng, tạm dừng, và điều hướng menu.  
- **`LevelLoader.java` (100%)** – Thiết kế **bản đồ (map)** sống động, hỗ trợ nhiều cấp độ chơi khác nhau.  
- **`Brick`, `NormalBrick`, `StrongBrick` (30%)** – Phối hợp thiết kế và xử lý va chạm giữa bóng và gạch.  
- **`PowerUp`, `ExpandPaddlePowerUp`, `FastBallPowerUp`, `LaserPaddlePowerUp`, `Bullet` (20%)** – Hỗ trợ triển khai logic tương tác giữa power-up và paddle.  
- **`Paddle.java` (50%)** – Xây dựng logic điều khiển di chuyển mượt mà, phản ứng chính xác theo bàn phím.  

> 🧠 *Phong là người giữ vai trò kiến trúc sư chính, đảm bảo hệ thống vận hành ổn định, logic rõ ràng và gameplay mượt mà.*

---

### 🎵 2. Sáng — **Lập trình viên hệ thống âm thanh & cấu trúc đa màn chơi**

**Tổng đóng góp: ~30%**

Sáng là người chịu trách nhiệm về **kiến trúc âm thanh**, **thiết kế hệ thống màn chơi (multi-level)** và các **lớp cơ sở logic** cho các đối tượng trong game.  
Anh đã mang lại cấu trúc hợp lý, giúp game có khả năng mở rộng dễ dàng.

**Chi tiết đóng góp:**
- **`App.java` (30%)** – Xây dựng hệ thống chọn **đa màn chơi (multi-level menu)** và tích hợp giao diện.  
- **`Music.java` (100%)** – Thiết kế **toàn bộ hệ thống âm thanh**, bao gồm nhạc nền, hiệu ứng khi phá gạch, nhận power-up và bật/tắt nhạc nền.  
- **`GameManager.java` (33.33%)** – Xây dựng **hệ thống render & update chuẩn**, đồng bộ âm thanh và hiệu ứng.  
- **`Ball.java` (100%)** – Triển khai cấu trúc vận hành của bóng, xử lý va chạm và phản xạ vật lý.  
- **`Brick`, `NormalBrick`, `StrongBrick` (40%)** – Phối hợp xây dựng hình ảnh, trạng thái và phản hồi âm thanh khi phá gạch.  
- **`PowerUp`, `ExpandPaddlePowerUp`, `FastBallPowerUp`, `LaserPaddlePowerUp`, `Bullet` (20%)** – Hỗ trợ thiết kế lớp cha và hiệu ứng khi kích hoạt.  
- **`MovableObject`, `GameObject` (60%)** – Xây dựng **logic nền tảng** cho các lớp cha, quản lý vị trí, vận tốc và va chạm.  
- **`Paddle.java` (25%)** – Tham gia tối ưu hệ thống vẽ và cập nhật vị trí paddle.

> 🎧 *Sáng là “nhạc trưởng” của hệ thống âm thanh, giúp trải nghiệm game thêm sinh động và chuyên nghiệp.*

---

### 🧱 3. Tâm — **Lập trình viên giao diện & cơ chế Power-Up**

**Tổng đóng góp: ~30%**

Tâm phụ trách phần **giao diện hiển thị trong game (UI)**, **các cơ chế gameplay nâng cao**, và **hệ thống power-up**.  
Anh đã giúp game thêm phong phú, trực quan và hấp dẫn hơn về mặt trải nghiệm người chơi.

**Chi tiết đóng góp:**
- **`App.java` (20%)** – Xây dựng giao diện khởi tạo và tinh chỉnh các yếu tố hình ảnh trong menu game.  
- **`GameManager.java` (33.33%)** – Phát triển các **tính năng gameplay** như **máu, điểm số, mạng sống, và hiệu ứng power-up**.  
- **`Brick`, `NormalBrick`, `StrongBrick` (30%)** – Cập nhật giao diện va chạm, hiệu ứng phá gạch và phản hồi người chơi.  
- **`PowerUp`, `ExpandPaddlePowerUp`, `FastBallPowerUp`, `LaserPaddlePowerUp`, `Bullet` (60%)** – **Triển khai chính toàn bộ cơ chế power-up**, đảm bảo tương tác mượt mà với paddle và bóng.  
- **`MovableObject`, `GameObject` (20%)** – Hỗ trợ hoàn thiện cơ chế di chuyển và kế thừa giữa các lớp.  
- **`Paddle.java` (25%)** – Cải tiến hiển thị và hiệu ứng paddle trong game.

> 🪄 *Tâm đóng góp lớn trong việc hoàn thiện tính năng gameplay và hệ thống power-up, giúp game trở nên hấp dẫn và có chiều sâu hơn.*

---

### 📊 Tổng kết tỉ lệ đóng góp

| Thành viên | Tỉ lệ đóng góp | Vai trò chính |
|-------------|----------------|----------------|
| 🧑‍💻 **Phong** | **40%** | Lập trình chính, vòng lặp game, logic gameplay |
| 🎵 **Sáng** | **30%** | Hệ thống âm thanh, cấu trúc màn chơi, lớp nền |
| 🧱 **Tâm** | **30%** | Giao diện, Power-Up, tính năng mở rộng |

---

> 💬 *Cả ba thành viên đều phối hợp nhịp nhàng, hỗ trợ lẫn nhau trong quá trình phát triển.  
Kết quả là một sản phẩm hoàn chỉnh, mượt mà và thể hiện rõ tinh thần làm việc nhóm cũng như khả năng ứng dụng lập trình hướng đối tượng (OOP) trong phát triển game.*

---
