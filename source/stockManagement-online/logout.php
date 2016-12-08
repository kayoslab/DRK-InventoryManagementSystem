<?php
    session_start();
    unset($_SESSION["valid"]);
    header("Location: index.php");
?>
