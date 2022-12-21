package nl.tudelft.sem.template.commons.entities.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * An event (notification) which will be sent to users
 * This will be saved to the database
 */
@Data
@NoArgsConstructor
public class Event implements Serializable {
    private static final long serialVersionUID = 2839418384L;

    private int id;
}
