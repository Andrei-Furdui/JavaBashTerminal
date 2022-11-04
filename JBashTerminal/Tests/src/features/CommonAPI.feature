Feature: Test the functionality exposed by the common package from the Windows module

  @OS_API
  Scenario: Test the OS characteristics API
    Given the Helper class is called
    When the getOSName method is used
    And the getOSVersion method is used
    And specifying the comma between their results
    Then the result should return the following message:
      | message          |
      | Windows 10, 10.0 |


  @TOUCH_LS
  Scenario Outline: Test the TOUCH and LS for commands ran in PWD
  The TOUCH command is executed for files with and without an extension
    Given the touch command is executed to create the "<file_dir>"
    When the ls command for the current directory is executed
    Then the new "<file_dir>" should be listed among the other existing ones
    Examples:
      | file_dir          |
      | test.txt          |
      | test_no_extension |
