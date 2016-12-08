    <div class = "container form-signin">
        <h2 class="form-signin-heading">Anmelden</h2>
        <form class = "form-signin" role = "form" action = "<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method = "post">
            <h4 class="form-signin-heading"><?php echo $msg; ?></h4>

            <!--<label for="username" class="sr-only">Benutzername</label>-->
            <input type="username" name="username" class="form-control" placeholder="Benutzername" required autofocus>

            <!--<label for="password" class="sr-only">Passwort</label>-->
            <input type="password" name="password" class="form-control" placeholder="Passwort" required>

            <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit">Einloggen</button>
        </form>
    </div> <!-- /container -->
