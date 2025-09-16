## [$MAVEN_DOWNLOAD_URL] has to be set in environment
Most projects use this plugin to pull dependencies from our internal package repository.

You need to set the right environment variables, here is how:

### Get Token from JFrog

Go to https://repo.sup-logistik.de/ - You may have to login, with either your EL or SuP account.

On the top right, press your account.
![](./images/jfrog_your_profile.png)

Then "Set Me Up":
![](./images/jfrog_set_me_up.png)

Select "Maven":
![](./images/jfrog_select_maven.png)

Select the "maven-private-releases" repository:
![](./images/jfrog_select_repository.png)

You may need to enter your password again:
![](./images/jfrog_token_enter_password.png)

Press Generate Token. Your Token is shown. Press Copy to Copy.
![](./images/jfrog_copy_token.png)

### Set Environment Variables

Next, Set your token in your environment, either in your bash profile (Linux / WSL) or on windows via the UI: https://superuser.com/questions/1861276/how-to-set-a-folder-to-the-path-environment-variable-in-windows-11
(Hint: On windows, even when localized, it may be faster to simply enter "env" in the search, the right panel should show up. The guide details it.)

Set these environment variables:

MAVEN_DOWNLOAD_URL="https://repo.sup-logistik.de/artifactory/default-maven-virtual"
MAVEN_USER="YOUR_USER_HERE"
MAVEN_PASSWORD="YOUR_TOKEN_HERE"

Your username is your email, and the token is the previously aquired token from JFrog.

This should now just work!