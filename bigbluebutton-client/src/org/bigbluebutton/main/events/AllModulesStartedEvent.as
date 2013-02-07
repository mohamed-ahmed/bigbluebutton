package org.bigbluebutton.main.events
{
  import flash.events.Event;
  
  public class AllModulesStartedEvent extends Event
  {
    
    public static const ALL_MODULES_STARTED:String = "all modules has started event";
    
    public function AllModulesStartedEvent()
    {
      super(ALL_MODULES_STARTED, true, false);
    }
  }
}