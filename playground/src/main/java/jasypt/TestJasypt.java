package jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;


/**
 * NOT a microservice within this project
 * A Playground that can be used for encrypt and decrypt passwords using Jasypt
 * */
public class TestJasypt {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(System.getenv("TWITTER_TO_KAFKA_JASYPT")); // Fetching the password from env variables: ~/.zshrc or ~/.bash_profile
        standardPBEStringEncryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
        String result = standardPBEStringEncryptor.encrypt("password_or_string_to_encrypt");
        System.out.println(result);
//        System.out.println(standardPBEStringEncryptor.decrypt(result));
    }
}
