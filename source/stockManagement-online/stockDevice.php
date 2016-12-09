<body>
    <?php include 'nav.php'?>
    <div class = "container">
        </br>
        <?php
                include 'connect.php';
                $sql = "SELECT `id`, `firstname`, `name` FROM `User` WHERE `id` = " . $_SESSION['userId'] . ";";
                $result = mysql_query($sql);
                $row = mysql_fetch_array($result);
                if ($result) {
                    echo 'Eingeloggt als: ' . $row['firstname'] . ' ' . $row['name'];
                }
                echo "</br> \n </br> \n </br> \n";
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>
