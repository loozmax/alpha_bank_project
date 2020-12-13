<?php
    session_start();
    if (!$_SESSION['user']) {
        header('Location: /');
    }

require_once 'request_delete_edit.php';
require_once 'vendor/connect.php';


?>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>
    <a href="vendor/logout.php" class="logout">Выйти</a>
    <a class="news" href="vendor/news.php">Новости</a>
        <form class="msg-wall" method="post" action="/vendor/wall.php">
            <p>
                Стена сообщений:
            </p>
            <input type="text" name="wall" placeholder="Что у вас нового?">
            <button type="submit">Опубликовать</button>

            <?php foreach($check_message as $row) { ?>
            <div class="content">
                <p class="msg"><?= $row['message'] ?></p>
                <a class="delete" href="?del=<?= $row['id'] .','. $row['date'] ?>"><img src='images/trash.png'></a>
                <a class="edit" href="update.php?id=<?= $row['id'] . ',' . $row['date'] ?>"><img src="images/edit.png"></a>
            </div>
            <?php } ?>
            </div>
        </form>
</body>
</html>




