
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
                    <a class="navbar-brand" href="#">Stock Management</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Home</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="logout.php">Abmelden</a></a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>
        </br>
        </br>
        </br>
        <div class = "container">
            <?php
                    include 'connect.php';
                    $sql = "SELECT `id`, `firstname`, `name` FROM `User` WHERE `id` = " . $_SESSION['userId'] . ";";
                    $result = mysql_query($sql);
                    $row = mysql_fetch_array($result);
                    if ($result) {
                        echo 'Eingeloggt als: ' . $row['firstname'] . ' ' . $row['name'];
                    }
            ?>
        </div> <!-- /container -->
