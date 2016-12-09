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
                        <li><a href="./stock.php?type=1">Geräte</a></li>
                        <li><a href="./stock.php?type=2">Medizinisches Material</a></li>
                        <li><a href="./stock.php?type=3">Versorgungsmaterial</a></li>
                    </ul>
                </li>
                <li><a href="./inventory.php">Inventar</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <a href="logout.php"><button type="button" class="btn btn-danger navbar-btn">Abmelden</button></a>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
