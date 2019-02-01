#!groovy

node {
   def mvnHome
   def buildnumber

    env.JAVA_HOME="${tool 'Jenkins JDK'}"
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}:/usr/local/bin"
    env.FLINK_DIST = "/Users/Shared/Jenkins/Downloads"
    env.DOCKER_USER="dineshpillai"


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
          stage('Docker build and push flink-streamer-legal'){

                sh "flink-streamer-legal/build.sh --from-archive ${env.FLINK_DIST}/flink-1.7.1-bin-scala_2.11.tgz --job-jar flink-streamer-legal/target/flink-streamer-legal-*.jar --image-name dineshpillai/flink-streamer-legal:${buildnumber}"
                sh "cat ~/mypassword.txt|docker login --username $DOCKER_USER --password-stdin"
                sh "docker push dineshpillai/flink-streamer-legal:${buildnumber}"
          }
          stage ('Docker test'){

                sh "FLINK_DOCKER_IMAGE_NAME=dineshpillai/flink-streamer-legal:${buildnumber} FLINK_JOB=io.github.dineshgpillai.StreamingJob FLINK_JOB_ARGUMENTS=/legal-ex12-scsa-2014-new-york.xml  docker-compose -f flink-streamer-legal/docker-compose.yml up"


          }
          stage ('Prepare K8s deployments'){
                //set up the environment variables
                sh "export FLINK_IMAGE_NAME=dineshpillai/flink-streamer-legal:${buildnumber} export FLINK_JOB=io.github.dineshgpillai.StreamingJob export FLINK_JOB_ARGUMENTS=/legal-ex12-scsa-2014-new-york.xml export FLINK_JOB_PARALLELISM=1"

                //subst
                sh "envsubst < "flink-streamer-legal/manifests/job-cluster-job-template.yaml" > "flink-streamer-legal/manifests/job-cluster-job.yaml""
                sh "envsubst < "flink-streamer-legal/manifests/task-manager-deployment-template.yaml" > "flink-streamer-legal/manifests/task-manager-deployment.yaml""

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
