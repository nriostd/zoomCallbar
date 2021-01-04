import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.talkdesk.ZoomMiddleware.ZoomService;
import org.json.*;

public class ZoomServiceTest {

  @Test
  private void Testcreate_tokenSuccess(){

    //assert that no exception is thrown
    assertDoesNotThrow(() -> ZoomService.create_token());
  }

  @Test
  private void TestPresenceNotification(){
    Notification presenceNotification = new Notification();
    presenceNotification.setEvent("user.presence_status_updated");
    Payload presencePayload = new Payload();
    presencePayload.setAccountId("112244")
    com.talkdesk.ZoomMiddleware.model.Object presenceObject = new com.talkdesk.ZoomMiddleware.model.Object();
    presenceObject.setDateTime("2019-11-26T20:13:57Z");
    presenceObject.setEmail("kp@talkdesk.com");
    presenceObject.setId("KfUi5raET_aHM5IYpIKLZg");
    presenceObject.setPresenceStatus("Available");
    presencePayload.setObject(presenceObject);
    presenceNotification.setPayload(presencePayload);
    assertDoesNotThrow(() -> ZoomService.process_notification(presenceNotification));
  }

  @Test
  private void TestUserCreatedNotification(){
    Notification UserCreatedNotification = new Notification();
    UserCreatedNotification.setEvent("user.created");
    Payload userCreated = new Payload();
    userCreated.setAccountId("112244");
    com.talkdesk.ZoomMiddleware.model.Object createdObject = new com.talkdesk.ZoomMiddleware.model.Object();
    createdObject.setId("123456");
    createdObject.setAdditionalProperty("first_name", "Peter");
    createdObject.setAdditionalProperty("last_name", "Parker");
    createdObject.setEmail("pparker@gmail.com");
    userCreated.setObject(createdObject);
    UserCreatedNotification.setPayload(userCreated);
    assertDoesNotThrow(() -> ZoomService.process_notification(UserCreatedNotification));
  }

  @Test
  private void TestUserDeletedNotification(){
    Notification UserDeletedNotification = new Notification();
    UserDeletedNotification.setEvent("user.deleted");
    Payload userDeleted = new Payload();
    userDeleted.setAccountId("112244");
    com.talkdesk.ZoomMiddleware.model.Object deletedObject = new com.talkdesk.ZoomMiddleware.model.Object();
    deletedObject.setId("123456");
    deletedObject.setAdditionalProperty("first_name", "Peter");
    deletedObject.setAdditionalProperty("last_name", "Parker");
    deletedObject.setEmail("pparker@gmail.com");
    userDeleted.setObject(deletedObject);
    UserDeletedNotification.setPayload(userDeleted);
    assertDoesNotThrow(() -> ZoomService.process_notification(UserCreatedNotification));
  }

  @Test
  private void TestUserUpdatedNotification(){
    Notification UserUpdatedNotification = new Notification();
    UserUpdatedNotification.setEvent("user.updated");
    Payload userUpdated = new Payload();
    userUpdated.setAccountId("112244");
    com.talkdesk.ZoomMiddleware.model.Object updatedObject = new com.talkdesk.ZoomMiddleware.model.Object();
    updatedObject.setId("123456");
    updatedObject.setEmail("peterparker@gmail.com");
    com.talkdesk.ZoomMiddleware.model.Object oldObject = new com.talkdesk.ZoomMiddleware.model.Object();
    oldObject.setId("123456");
    oldObject.setEmail("pparker@gmail.com");
    userUpdated.setAdditionalProperty("old_object", oldObject);
    UserUpdatedNotification.setPayload(userUpdated);
    assertDoesNotThrow(() -> ZoomService.process_notification(UserUpdatedNotification));
  }

  @Test
  private void TestMalformedNotification(){
    Notification UserCreatedNotification = new Notification();
    UserCreatedNotification.setEvent("account.update");
  }

}
