# Fee Calculator

This is a small java program, offering an API to calculate the monthly membership rate of flatbond for a tenant.

## Getting Started

To run this program, download it directly from the git repo, or just clone it.
The following are required to make it run :
- Maven 3
- Java JDK 8 (I have been using jdk1.8.0_202 to make this app)
- [Optional] Postman to send a few test requests.
- and that's it !


### Installing

Provided you have Maven installed, just run the classic `mvn clean install` at the root of the project.

Now you need to properly configure the app, according to your needs. There are 4 files to configure :
- client-config.json
- divisions-config.json
- areas-config.json
- branches-config.json


Example for client-config

		{
			"name": "client_a",
			"divisions": ["division_a", "division_b"],
			"config": {
				"has_fixed_membership_fee": false,
				"fixed_membership_fee_amount": 0
			}
		}

Every config file has a name, and a config object. This config object can be used to set a specific value for the membership fee. However, 
whatever is configured in the configuration, the child's config object will always override it. 
If you whish to use the configuration of the parent, leave the config field empty.
		
Example for divisions

[

		{
			"name": "division_a",
			"areas": ["area_a", "area_b"],
			"config": {
				"has_fixed_membership_fee": false,
				"fixed_membership_fee_amount": 0
			}
		},
		
		
		{
			"name": "division_b",
			"areas": ["area_c"],
			"config": {
				"has_fixed_membership_fee": true,
				"fixed_membership_fee_amount": 4500000
			}
		}

]


## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds
