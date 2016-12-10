<body>
    <?php include 'nav.php'?>
    <div class = "container">
        </br>
        <?php
                include 'connect.php';
                $sql = "SELECT `id`, `firstname`, `name`, `username`, `mail` FROM `User` WHERE `id` = " . $_SESSION['userId'] . ";";
                $result = mysql_query($sql);
                $row = mysql_fetch_array($result);
                if ($result) {

                }
                echo "</br> \n </br> \n";
                echo "<div class=\"panel panel-info\"> \n";
                echo "<div class=\"panel-heading\"> \n";
                echo "<h3 class=\"panel-title\">Benutzerdaten</h3> \n";
                echo "</div> \n";
                    echo "<div class=\"row\"> \n";
                        echo "<div class=\"col-md-4 col-md-offset-1\"> \n";
                            echo "</br> \n";
                            echo "<b>Eingeloggt als:</b> " . $row['username'] . " \n";
                            echo "</br> \n";
                            echo "<b>Vorname:</b> " . $row['firstname'] . " \n";
                            echo "</br> \n";
                            echo "<b>Nachname:</b> " . $row['name'] . " \n";
                            echo "</br> \n";
                            echo "</br> \n";
                        echo "</div> \n";
                        echo "<div class=\"col-md-4 col-md-offset-2\"> \n";
                            echo "</br> \n";
                            echo "<b>E-Mail:</b> " . $row['mail'] . " \n";
                            echo "</br> \n";
                            echo "</br> \n";
                        echo "</div> \n";
                    echo "</div> \n";
                echo "</div> \n";

                include 'newsDanger.php';
                include 'newsWarnings.php';
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>
