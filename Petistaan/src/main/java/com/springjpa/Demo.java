package com.springjpa;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.dto.PetDTO;
import com.springjpa.service.OwnerService;
import com.springjpa.service.PetService;
import com.springjpa.util.InputUtil;

import lombok.RequiredArgsConstructor;

@PropertySource("classpath:messages.properties")
@RequiredArgsConstructor
@SpringBootApplication
public class Demo implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
	
	private final OwnerService ownerService;
	private final PetService petService;

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			do {
				System.out.println("Welcome to Petistaan");
				int menuOption=InputUtil.acceptMenuOption(sc);
				switch(menuOption)
				{
				case 1:
					OwnerDTO ownerDTO=InputUtil.acceptOwnerDetailsToSave(sc);
					PetDTO petDTO=InputUtil.acceptPetDetailsToSave(sc);
					ownerDTO.setPetDTO(petDTO);
					ownerService.saveOwner(ownerDTO);
					System.out.println("Saved owner successfully.");
					break;
				case 2:
					int ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					ownerDTO = ownerService.findOwner(ownerId);
					System.out.println(String.format("Found owner with ownerId %s.", ownerId));
					System.out.println(ownerDTO);
					break;
				case 3:
					ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					String petName = InputUtil.acceptPetDetailsToUpdate(sc);
					ownerService.updatePetDetails(ownerId, petName);
					System.out.println(
							String.format("Updated petName to %s for owner with ownerId %s.", petName, ownerId));
					break;
				case 4:
					ownerId = InputUtil.acceptOwnerIdToOperate(sc);
					ownerService.deleteOwner(ownerId);
					System.out.println(String.format("Deleted owner with ownerId %s.", ownerId));
					break;
				case 5:
					List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
					System.out.println(String.format("There are %s owners.", ownerDTOList.size()));
					ownerDTOList.forEach(System.out::println);
					break;
				case 6:
					int petId = InputUtil.acceptPetIdToOperate(sc);
					petDTO = petService.findPet(petId);
					System.out.println(String.format("Found pet with petId %s.", petId));
					System.out.println(petDTO);
					break;
				case 7:
					double averageAge = petService.findAverageAgeOfPet();
					System.out.println(String.format("Average age of pet is %s years.", averageAge));
					break;
				case 8:
					int pageNumber=InputUtil.acceptPageNumberToOperate(sc);
					int pageSize=InputUtil.acceptPageSizeToOperate(sc);
					List<Object[]> detailsList=ownerService
							.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageNumber-1, pageSize);
					System.out.println(
							String.format("Showing %s records on page number %s.", detailsList.size(), pageNumber));
					detailsList.forEach(details -> System.out
							.println(String.format("ownerId: %s, firstName: %s, lastName: %s, petName: %s", details[0],
									details[1], details[2], details[3])));
		
				}
				

			} while (InputUtil.wantToContinue(sc));

		} catch (Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
		}

	}

}
