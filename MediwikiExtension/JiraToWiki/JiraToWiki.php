<?php
# Alert the user that this is not a valid access point to MediaWiki if they try to access the special pages file directly.
if ( !defined( 'MEDIAWIKI' ) ) {
	echo <<<EOT
To install my extension, put the following line in LocalSettings.php:
require_once( "\$IP/extensions/JiraToWiki/JiraToWiki.php" );
EOT;
	exit( 1 );
}

$wgExtensionCredits['specialpage'][] = array(
	'path' => __FILE__,
	'name' => 'JiraToWiki',
	'author' => 'Genesys Technical Publications',
	'url' => 'juan.lara@genesys.com',
	'descriptionmsg' => 'jiratowiki-desc',
	'version' => '0.0.0',
);

$wgAutoloadClasses['SpecialJiraToWiki'] = __DIR__ . '/SpecialJiraToWiki.php'; # Location of the SpecialJiraToWiki class (Tell MediaWiki to load this file)
$wgMessagesDirs['JiraToWiki'] = __DIR__ . "/i18n"; # Location of localisation files (Tell MediaWiki to load them)
$wgExtensionMessagesFiles['JiraToWikiAlias'] = __DIR__ . '/JiraToWiki.alias.php'; # Location of an aliases file (Tell MediaWiki to load it)
$wgSpecialPages['JiraToWiki'] = 'SpecialJiraToWiki'; # Tell MediaWiki about the new special page and its class name