
cloud {

	// Mandatory. The name of the cloud, as it will appear in the Cloudify UI.
	name = "Azure"
	configuration {
	
		// Mandatory - Azure IaaS cloud driver.
		className "org.cloudifysource.esc.driver.provisioning.azure.MicrosoftAzureCloudDriver"
		
		// Optional. The template name for the management machines. Defaults to the first template in the templates section below.
		managementMachineTemplate "SMALL_LINUX"
		
		// Optional. Indicates whether internal cluster communications should use the machine private IP. Defaults to true.
		connectToPrivateIp true
	}

	provider {
	
		// Optional 
		provider "azure"
			
		/*************************************************************************************************************************
		 * Optional. The HTTP/S URL where cloudify can be downloaded from by newly started machines. Defaults to downloading the *
		 * cloudify version matching that of the client from the cloudify CDN.													 *
		 * Change this if your compute nodes do not have access to an internet connection, or if you prefer to use a			 *
		 * different HTTP server instead.																						 *
		************************************************ *************************************************************************/
		
		// cloudifyUrl "http://repository.cloudifysource.org/org/cloudifysource/2.2.0/gigaspaces-cloudify-2.2.0-m4-b2493-70.zip"
		
		machineNamePrefix "cloudify_agent_"
		
		
		managementOnlyFiles ([])
		
		managementGroup "cloudify_manager"
		numberOfManagementMachines 1
		
		reservedMemoryCapacityPerMachineInMB 1024
		
		sshLoggingLevel "WARNING"
		
		
	}
	
	user {
		
		// Azure subscription id
		user "ENTER_SUBSCRIPTION_ID"
			
	}

	templates ([
				SMALL_LINUX : template{
				
					imageId "OpenLogic__OpenLogic-CentOS-62-20120531-en-us-30GB.vhd"
					machineMemoryMB 1600
					hardwareId "Small"
					localDirectory "upload"
					
					username "ENTER_USER_NAME"
					password "ENTER_PASSWORD"
					
					remoteDirectory "/home/${username}/gs-files"
					
					custom ([
					
						// Optional. each availability set represents a different fault domain.
						
						"azure.availability.set" : "ENTER_AVAILABILITY_SET",
						
						// Choose whether do deploy this instance in Staging or Production environment. defaults to Staging
						
						"azure.deployment.slot": "ENTER_DEPLOYMENT_SLOT",
						
						/**************************************************************
						 * Mandatory only for templates used for management machines. *
						 * Put this file under the path specified in 'localDirectory' *
						***************************************************************/
						
						"azure.pfx.file": "ENTER_PFX_FILE",
						
						// Password that was used to create the certificate
						
						"azure.pfx.password" : "ENTER_PFX_PASSWORD"
					])
				}
			])
			
	custom ([
			
		/*****************************************************************************************
		 * A Virtaul Network Site name.																 *
		 * All VM's will belong to this network site. 												 *
		 * If the specified network site does not exist, it will be created automatically for you.	 *
		 * in this case, you must specify the 'azure.address.space' property					 *	 
		******************************************************************************************/
		
		"azure.networksite.name" : "ENTER_VIRTUAL_NETWORK_SITE_NAME",
		
		/***************************************************************************************
		 * CIDR notation specifying the Address Space for your Virtaul Network. 			   *
		 * All VM's will be assigned a private ip from this address space.					   *
		****************************************************************************************/
		
		"azure.address.space" : "ENTER_ADDRESS_SPACE",
		
		/****************************************************************************************	
		 * An Affinity Group name.																*
		 * if the specified group does not exist, one will be created automatically for you.	*
		 * in this case, you must specify the 'azure.affinity.location' property				*
		*****************************************************************************************/
		
		"azure.affinity.group" : "ENTER_AFFINITY_GROUP",

		/********************************************************************************************************************************
		 * The MS Data Center location. 																								*
		 * All VM's will be launched onto this Data Center. see http://matthew.sorvaag.net/2011/06/windows-azure-data-centre-locations/	*	
		 * Mandatory only if the affinity group specifed above is not a pre-existing one.												*
		*********************************************************************************************************************************/
		
		"azure.affinity.location" : "ENTER_LOCATION",
		
		/*****************************************************************************************
		 * A Storage Account name.																 *
		 * All OS Disk Images will be stored in this account. 									 *
		 * If the specified account does not exist, it will be created automatically for you.	 *
		******************************************************************************************/

		"azure.storage.account" : "ENTER_STORAGE_ACCOUNT",
		
		// Specify whether or not to delete the network (if found) when you execute a teardown command. 
		
		/*************************************************************************************************************************
		 * If set to 'true', the storage account, affinity group, and network specified above will be deleted upon teardown.	 *
		 * NOTE : if you are using pre exsisting services and you dont want them to be deleted, please set this value to 'false' *
		**************************************************************************************************************************/
		
		"azure.cleanup.on.teardown" : "true",

		// Enable/Disable Cloud Requests Logging. 
		
		"azure.wireLog": "false"
	])
}

