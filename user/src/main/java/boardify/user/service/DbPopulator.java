package boardify.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {

    private Service service;

    @Autowired
    public CommandLineAppStartupRunner(Service service){

        this.service = service;
    }

    @Override
    public void run(String...args) throws Exception {

        service.registerUser("x", "x");
        service.registerUser("y", "y");
        service.registerUser("z", "z");
    }
}
