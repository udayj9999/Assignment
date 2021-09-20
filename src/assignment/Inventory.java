package assignment;

import java.sql.*;
import java.util.Scanner;

public class Inventory {
    public static void main(String[] args) throws Exception {

        String user;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Shopkeeper or Customer");
        user = scanner.next();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment", "root", "Uday@1407");

        int id;
        String name;
        int price;
        int quantity;
        PreparedStatement preparedStatement = null;
        switch (user) {
            case "shopkeeper":
                Scanner scanner1 = new Scanner(System.in);
                String option;
                System.out.println("Choose option");
                option = scanner1.next();
                switch (option) {
                    case "add":
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Enter id of product");
                        id = sc.nextInt();
                        System.out.println("Enter name for product");
                        name = sc.next();
                        System.out.println("Enter price of product");
                        price = sc.nextInt();
                        System.out.println("Enter price of product");
                        quantity = sc.nextInt();
                        String insert = "insert into product(id,name,price) values(?,?,?)";
                        preparedStatement = connection.prepareStatement(insert);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setInt(3, price);
                        preparedStatement.setInt(4, quantity);

                        int i = preparedStatement.executeUpdate();
                        System.out.println(i + " record added ..");

                        break;
                    case "remove":
                        Scanner sc2 = new Scanner(System.in);
                        int rId;
                        System.out.println("Enter the id which is to be removed");
                        rId = sc2.nextInt();

                        String remove = "delete from product where id = " + rId;
                        preparedStatement = connection.prepareStatement(remove);
                        int j = preparedStatement.executeUpdate();
                        System.out.println(j + " record removed");

                        break;
                    case "list":
                        String list = "select * from product";
                        preparedStatement = connection.prepareStatement(list);
                        ResultSet rs = preparedStatement.executeQuery();
                        while (rs.next()) {
                            System.out.print(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getInt(4));
                        }

                        break;
                    case "edit":
                        Scanner sc3 = new Scanner(System.in);
                        int eId;
                        System.out.println("Enter the id which is to be edit");
                        eId = sc3.nextInt();

                        System.out.println("Enter name for product");
                        name = sc3.next();
                        System.out.println("Enter price of product");
                        price = sc3.nextInt();
                        System.out.println("Enter quantity of product");
                        quantity = sc3.nextInt();

                        String edit = "update product set id = ?,name =? ,price =? ,quantity = ? where id = " + eId;
                        preparedStatement = connection.prepareStatement(edit);
                        preparedStatement.setInt(1, eId);
                        preparedStatement.setString(2, name);
                        preparedStatement.setInt(3, price);
                        preparedStatement.setInt(4, quantity);
                        int e = preparedStatement.executeUpdate();
                        System.out.println(e + " record edit");

                        break;
                    case "search":
                        Scanner sc4 = new Scanner(System.in);
                        String pName;
                        System.out.println("Enter the product name to be search");

                        pName = sc4.next();
                        String search = "select * from product where name =" + pName;
                        preparedStatement = connection.prepareStatement(search);
                        ResultSet rs1 = preparedStatement.executeQuery();
                        System.out.print(rs1.getInt(1) + " " + rs1.getString(1));

                        break;
                }


            case "customer":
                Scanner scanner2 = new Scanner(System.in);
                String option1;
                Character choice = null;

                do {
                    System.out.println("Choose option");
                    option1 = scanner2.next();
                    switch (option1) {
                        case "list":
                            String list = "select * from product";
                            preparedStatement = connection.prepareStatement(list);
                            ResultSet rs = preparedStatement.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getInt(4));
                            }

                            System.out.println("Do you want to continue");
                            choice = scanner2.next().charAt(0);
                            break;

                        case "search":
                            Scanner sc4 = new Scanner(System.in);
                            int pId;
                            System.out.println("Enter the product id to be search");

                            pId = sc4.nextInt();
                            String search = "select * from product where id =" + pId;
                            preparedStatement = connection.prepareStatement(search);
                            ResultSet rs1 = preparedStatement.executeQuery();
                            while (rs1.next()){
                                System.out.println(rs1.getInt("id") + " " + rs1.getString("name")+" "+rs1.getInt("price")+" "+rs1.getInt("pquantity"));
                            }
                            System.out.println("Do you want to continue");
                            choice = scanner2.next().charAt(0);
                            break;
                        case "buy":
                            Scanner sc5 = new Scanner(System.in);
                            int productId;
                            String cName;
                            int cardNumber;
                            int cvv;
                            int buyQuantity;

                            System.out.println("Enter product id");
                            productId = sc5.nextInt();
                            System.out.println("Enter product quantity");
                            buyQuantity = sc5.nextInt();
                            System.out.println("Enter Customer name");
                            cName = sc5.next();
                            System.out.println("Enter card number");
                            cardNumber = sc5.nextInt();
                            System.out.println("Enter cvv");
                            cvv = sc5.nextInt();

                            String buy = "insert into orders(id,name,creditcard,cvv,quntity) values(?,?,?,?,?)";
                            preparedStatement = connection.prepareStatement(buy);
                            preparedStatement.setInt(1, productId);
                            preparedStatement.setString(2, cName);
                            preparedStatement.setInt(3, cardNumber);
                            preparedStatement.setInt(4, cvv);
                            preparedStatement.setInt(5, buyQuantity);

                            int count = preparedStatement.executeUpdate();
                            System.out.println("product purchased successfully..." + count);

                            String decrementStock = "update product set quantity = (select quantity from product where id = ?) ";
                            System.out.println("Do you want to continue");
                            choice = scanner2.next().charAt(0);
                            break;

                    }
                } while (choice == 'Y' || choice =='y');

        }
    }

}