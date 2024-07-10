package supplyChain.supplychain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ProductDetails {

        @NotBlank
        private String name;
//        @NotBlank

        private Integer price;
        @NotNull
        private Integer quantity = 0;
        private String category;


        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }

        public void setPrice(Integer price){
            this.price = price;
        }
        public Integer getPrice(){
            return price;
        }
        public void setQuanity(Integer quantity){
            this.quantity = quantity;
        }
        public Integer getQuantity(){
            return quantity;
        }
        public void setCategory(String category){
            this.category = category;
        }
        public String getCategory(){
            return category;
        }


}
