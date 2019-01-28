#!groovy

node {
   def mvnHome
   def buildnumber

    env.JAVA_HOME="${tool 'Jenkins JDK'}"
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}:/usr/local/bin"


   try {
           stage('Preparation') { // for display purposes
              // Get some code from a GitHub repository
              sh "sudo -i -u jenkins"
               git 'ssh://git@github.com/dineshgpillai/flink-streamer-reference-data.git'
              // Get the Maven tool.
              // ** NOTE: This 'M3' Maven tool must be configured
              // **       in the global configuration.
              mvnHome = tool 'Jenkins Maven'
              buildnumber = "${env.BUILD_NUMBER}"
           }
           stage('Compile') {
              // Run the maven build
              if (isUnix()) {
                 sh "'${mvnHome}/bin/mvn' clean compile"
              }
           }
           stage('Test Package and Deploy Snapshot') {
             // Run the maven build
             if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore verify package deploy -Psurefire -Dbuildnumber=${buildnumber}-SNAPSHOT"
             }
          }
          stage('Docker build flink-streamer-legal'){

                sh "cd flink-streamer-legal"
                sh "pwd"
                sh "./build.sh --from-release --flink-version 1.6.0 --hadoop-version 2.8 --scala-version 2.11 --job-jar target/flink-streamer-legal-*.jar --image-name flink-streamer-legal-${buildnumber}"

          }
          stage('Deploy approval'){
              input "Deploy to prod?"
          }
          stage('Release') {
              if (isUnix()) {
                       sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package deploy -Prelease -Dbuildnumber=${buildnumber}"
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