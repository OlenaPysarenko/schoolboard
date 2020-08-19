package com.ua.schoolboard.persistence;

import javax.persistence.Embeddable;
import java.util.Date;
@Embeddable
public class Duration {
     private Date startDate;
     private Date endDate;
}
