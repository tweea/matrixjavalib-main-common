pipeline {
	agent {
		label 'docker-local'
	}
	options {
		quietPeriod(60)
		timestamps()
		buildDiscarder logRotator(numToKeepStr: '5')
	}
	environment {
		CREDENTIAL_CIBOT = credentials("${env.CREDENTIAL_CIBOT_ID}")
		CREDENTIAL_CIBOT_SONARQUBE = credentials("${env.CREDENTIAL_CIBOT_SONARQUBE_ID}")
		MAVEN_SETTINGS = "${env.AGENT_CONFIG}/maven/settings.xml"
		MAVEN_OPTS = '-Xmx512m'
	}
	stages {
		stage('Compile') {
			agent {
				docker {
					image 'nexus.tweea.net.cn:8003/maven:3.9-eclipse-temurin-11'
					args "$DOCKER_HOSTS"
					reuseNode true
				}
			}
			steps {
				sh 'mvn -B -s $MAVEN_SETTINGS clean compile'
			}
		}
		stage('Test') {
			agent {
				docker {
					image 'nexus.tweea.net.cn:8003/maven:3.9-eclipse-temurin-11'
					args "$DOCKER_HOSTS"
					reuseNode true
				}
			}
			steps {
				sh 'mvn -B -s $MAVEN_SETTINGS test'
			}
		}
		stage('Dependency-Check') {
			steps {
				sh "docker run --rm -i $DOCKER_HOSTS -v $WORKSPACE:/src:z -v $AGENT_CACHE/OWASP-Dependency-Check:/usr/share/dependency-check/data:z -u $AGENT_USER_ID nexus.tweea.net.cn:8003/owasp/dependency-check --scan /src --out /src --format XML --format HTML --format JSON --noupdate --disableOssIndex"
				dependencyCheckPublisher pattern: ''
			}
		}
		stage('SonarQube') {
			agent {
				docker {
					image 'nexus.tweea.net.cn:8003/maven:3.9-eclipse-temurin-17'
					args "$DOCKER_HOSTS"
					reuseNode true
				}
			}
			steps {
				sh 'mvn -B -s $MAVEN_SETTINGS sonar:sonar'
			}
		}
		stage('Publish') {
			agent {
				docker {
					image 'nexus.tweea.net.cn:8003/maven:3.9-eclipse-temurin-11'
					args "$DOCKER_HOSTS"
					reuseNode true
				}
			}
			steps {
				sh 'mvn -B -s $MAVEN_SETTINGS -DskipTests deploy site-deploy'
			}
		}
	}
}
