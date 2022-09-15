Contributing to EasyNotes
=====================

EasyNotes is open-source and you're welcome to contribute to it. You're encouraged to
submit [pull requests](https://github.com/NotesCommunity/easy-notes/pulls)
, [propose features and discuss issues](https://github.com/NotesCommunity/easy-notes/issues).

### Clone the Project

Clone the project on your local machine using the commands

```
git clone https://github.com/NotesCommunity/easy-notes.git
cd note-x
git remote add upstream https://github.com/NotesCommunity/easy-notes.git
```

### Create a Branch for your feature

Make sure your branch is up-to-date with ```dev``` and create a branch based on your name and
whether it is a bugfix or a feature.  (The name `github-username/feature/my-feature`
or `github-username/bugfix/foo-button-fixed` is an example).

```
Please use the convention -> github-username/feature/feature-name or github-username/bugfix/bug-name
```

Please follow the commands in order before you create your branch

```
git checkout master
git pull origin master
git checkout dev
git pull origin dev
git checkout -b github-username/feature/my-feature
```

### Write Code

Implement your feature or bug fix. Make sure you do a ``` git pull origin dev``` before making any
changes to avoid any merge conflicts.

Make sure that your app builds and is successfully installed on your mobile device without errors.

### Commit Changes

Make sure git knows your name and email address:

```
git config --global user.name "Your Name"
git config --global user.email "contributor@example.com"
```

Add the changed files to the staging area using [git add](https://git-scm.com/docs/git-add). Most
IDEs make this easy for you to do, so you won't need this command line version.
Writing [good commit logs](https://chris.beams.io/posts/git-commit/) is important. A commit log
should describe what changed and why.

```
git add .
git commit -m "Fixed Foo bug by changing bar"
git pull origin dev

or 
git commit -am "Fixed Foo bug by changing bar"
git pull origin dev
```

### Push to your branch

```
git push origin github-username/feature/my-feature
```

Make sure you format your code before you push as per the best practices for better and enhanced
readability. You can use the default code formatter provided by Android Studio by pressing **Ctrl +
Alt + L** on your windows device/**Alt + Shift + L** on your Linux device or **Command + Option +
L** on your Mac.

### Make a Pull Request

Go to https://github.com/NotesCommunity/easy-notes and select your name and feature/bugfix branch.
Click the 'Pull Request' button and fill out the form.
```[Please note to open a pull request to your team-lead branch only.]```

Pull requests are usually reviewed within a few days.

If code review requests changes (and it usually will) just `git push` the changes to your repository
on the same branch, and the pull request will be automatically updated.

### Check on Your Pull Request

Go back to your pull request after a few minutes/days and see whether it passed the code-review
Everything should be fine if your PR is green and successfully merged or code changes will be
requested by the maintainers.

### Thank You!!

Please do know that we really appreciate and value your time and work. Thank you for your
Contribution!!
