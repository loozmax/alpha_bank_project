<?php
    require_once '../news.php';
?>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<a class="my-wall" href="../profile.php">Моя стена</a>
<div class="all-news">
    <form class="news-form">
        <?php foreach($news as $row) { ?>
            <div class="not-mine-messages">
                <p>Сообщение: <?= $row['message'] ?></p>
                <p>Автор: <?= $row['login'] ?></p>
                <p>Дата: <?= $row['date'] ?></p>
            </div>
        <?php } ?>

        <?php foreach($my_msg as $prod) { ?>
            <div class="not-mine-messages mine">
                <p>Сообщение: <?= $prod['message'] ?></p>
                <p>Автор: <?= $prod['login'] ?></p>
                <p>Дата: <?= $prod['date'] ?></p>
                <a class="delete" href="?del=<?= $prod['id'] .','. $prod['date'] ?>"><img src='../images/trash.png'></a>
                <a class="edit" href="../update.php?id=<?= $prod['id'] . ',' . $prod['date'] ?>"><img src="../images/edit.png"></a>
            </div>
        <?php } ?>


    </form>
</div>
</body>
</html>