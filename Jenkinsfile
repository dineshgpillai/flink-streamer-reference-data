#!groovy

node {
   def mvnHome
   def buildnumber

    env.JAVA_HOME="${tool 'Jenkins JDK'}"
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

   try {
           stage('Preparation') { // for display purposes
              // Get some code from a GitHub repository
              sh "sudo -i -u jenkins"
               git 'ssh://git@github.com/dineshgpillai/mvn-docker-cd.git'
              // Get the Maven tool.
              // ** NOTE: This 'M3' Maven tool must be configured
              // **       in the global configuration.
              mvnHome = tool 'Jenkins Maven'
              buildnumber = "${env.BUILD_NUMBER}"

              sh "echo ${env.BUILD_NUMBER}"
              sh "echo ${env.JAVA_HOME}"
           }
           stage('Build SNAPSHOT') {
              // Run the maven build
              if (isUnix()) {
                 sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package deploy -Dbuildnumber=${buildnumber}-SNAPSHOT"
              } else {
                 bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
              }
           }
           stage('Release') {
              if (isUnix()) {
                       sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package deploy -Dbuildnumber=${buildnumber}"
                    } else {
                       bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
                    }
           }
       }
       catch (err) {

               currentBuild.result = "FAILURE"

                   //mail body: "project build error is here: ${env.BUILD_URL}" ,
                   //from: 'dinesh.g.pillai@gmail.com',
                   //replyTo: 'dinesh.g.pillai@gmail.com',
                   //subject: 'project build failed',

               throw err
           }
}