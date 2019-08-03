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

Example for areas



	[
		{
			"name": "area_a",
			"branches": ["branch_a", "branch_b"],
			"config": {
				"has_fixed_membership_fee": false,
				"fixed_membership_fee_amount": 0
			}
		},
		{
			"name": "area_b",
			"branches": ["branch_c"],
			"config": {
				"has_fixed_membership_fee": true,
				"fixed_membership_fee_amount": 500000
			}
		},
		{
			"name": "area_c",
			"branches": ["branch_d"]
		}

	]

Example for branches

	[
		{
			"name": "branch_a",
			"config": {
				"has_fixed_membership_fee": false,
				"fixed_membership_fee_amount": 0
			}
		},
		{
			"name": "branch_b",
			"config": {
				"has_fixed_membership_fee": true,
				"fixed_membership_fee_amount": 30000
		}
		},
		{
			"name": "branch_c"
		},
		{
			"name": "branch_d"
		}
		

	]

	
There are rules to follow:

- The client must be unique
- The client can have divisions
- There can be several divisions
- Divisions can have areas
- There can several areas
- The areas can have several branches
- There can several branches

This is basically a tree, the root being the client, its leaves are divisions, then areas and finally branches.

I have aslo added verifications on the tree :
- Each division/area/branch assiged in a parent must also be configured
- Each division/area/branch must be assigned.
	
	


## Running the tests

Simply run `mvn test` or `mvn clean install` if you want to rebuild it.

## Running the program

Once the configuration completed, and the .jar file built, you can run the app with the following command :

`java -jar fee-calculator-0.0.1-shaded.jar --conf [path to config files]`

With the --conf option being the path to your configuration files (relative or absolute).
If you don't set any --conf parameter, the app will search in the `conf` folder at the root of the project.


## Using the program

This app only offers a single API, that returns the membership fee based on the rent, the rent period and the associated branch.

The exposed API is POST http://localhost:8080/membership/fee

Input example:

	{
		"rent" : 13000,
		"rent_period" : "week",
		"organization_unit" : "branch_a"
	}

The rent value is in ***pence***.

The output is as follow : 

	{
		"rent": 13000,
		"rent_period": "week",
		"organization_unit": "branch_a",
		"membershipFee": 15600
	}

The membership fee value is also in ***pence***.

You can find a post man format request that you can import at the root of the project.

You can also use this curl request : 

	curl -X POST \
	  http://localhost:8080/membership/fee \
	  -H 'Accept: application/json' \
	  -H 'Cache-Control: no-cache' \
	  -H 'Connection: keep-alive' \
	  -H 'Content-Type: application/json' \
	  -H 'Host: localhost:8080' \
	  -H 'accept-encoding: gzip, deflate' \
	  -H 'cache-control: no-cache' \
	  -d '{
		"rent": 13000,
		"rent_period": "week",
		"organization_unit": "branch_a"
	}'




