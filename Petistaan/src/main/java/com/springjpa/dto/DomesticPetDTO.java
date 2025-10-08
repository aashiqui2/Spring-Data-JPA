package com.springjpa.dto;

import java.time.LocalDate;

import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString(callSuper=true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO{
	
	private LocalDate birthDate;
	
	@Builder
	public DomesticPetDTO(int id,String name,Gender gender,PetType type,OwnerDTO ownerDTO,LocalDate birthDate) {
		super(id,name,gender,type,ownerDTO);
		this.birthDate=birthDate;		
	}

}
/*
     if You add @builder then it will do this for you
     -----------------------------------------------
 
	   public class DomesticPetDTO {
	    private LocalDate birthDate;
	
	    // Lombok added this constructor (used by builder)
	    private DomesticPetDTO(LocalDate birthDate) {
	        this.birthDate = birthDate;
	    }
	
	    // Lombok generated the builder() method
	    public static DomesticPetDTOBuilder builder() {
	        return new DomesticPetDTOBuilder();
	    }
	
	    // Generated getter & setter (from @Data)
	    public LocalDate getBirthDate() {
	        return birthDate;
	    }
	
	    public void setBirthDate(LocalDate birthDate) {
	        this.birthDate = birthDate;
	    }
	
	    // toString() from @Data
	    @Override
	    public String toString() {
	        return "DomesticPetDTO(birthDate=" + this.getBirthDate() + ")";
	    }
	
	    // equals() and hashCode() also generated (from @Data)
	
	    // === Inner Builder Class ===
	    public static class DomesticPetDTOBuilder {
	        private LocalDate birthDate;
	
	        DomesticPetDTOBuilder() { }
	
	        // Setter-like method (fluent API)
	        public DomesticPetDTOBuilder birthDate(LocalDate birthDate) {
	            this.birthDate = birthDate;
	            return this;
	        }
	
	        // Build method (creates the DTO)
	        public DomesticPetDTO build() {
	            return new DomesticPetDTO(this.birthDate);
	        }
	
	        @Override
	        public String toString() {
	            return "DomesticPetDTO.DomesticPetDTOBuilder(birthDate=" + this.birthDate + ")";
	        }
	    }
	}
*/
