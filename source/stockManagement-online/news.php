<body>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="./index.php">Stock Management</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="./index.php">News</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Lager<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Geräte</a></li>
                            <li><a href="#">Medizinisches Material</a></li>
                            <li><a href="#">Versorgungsmaterial</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="logout.php">Abmelden</a></a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>
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
                echo "</br> \n </br> \n </br>";
                include 'newsDanger.php';
                include 'newsWarnings.php';
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>
