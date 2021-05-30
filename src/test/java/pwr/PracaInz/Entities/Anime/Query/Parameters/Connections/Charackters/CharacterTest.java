package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    @Test
    void CharacterBuilder_NoParams_throwException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> Character.getCharacterBuilder().buildCharacter());

        assertEquals(exception.getMessage(), "Character should posses at least 1 parameter!");
    }

    @Test
    void CharacterBuilder_Id_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nid\n}");
    }

    @Test
    void CharacterBuilder_ManyId_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .id()
                .id()
                .id()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nid\n}");
    }

    @Test
    void CharacterBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void CharacterBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .id()
                .id()
                .id()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void CharacterBuilder_name_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .name()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nname {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .name()
                .name()
                .name()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nname {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n}");
    }

    @Test
    void CharacterBuilder_NameWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .name()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nname {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyNameWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .name()
                .name()
                .name()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nname {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n}");
    }

    @Test
    void CharacterBuilder_Image_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .image()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nimage {\nlarge\nmedium\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyImage_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .image()
                .image()
                .image()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nimage {\nlarge\nmedium\n}\n}");
    }

    @Test
    void CharacterBuilder_ImageWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .image()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nimage {\nlarge\nmedium\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyImageWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .image()
                .image()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nimage {\nlarge\nmedium\n}\n}");
    }

    @Test
    void CharacterBuilder_Description_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .description()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndescription\n}");
    }

    @Test
    void CharacterBuilder_ManyDescription_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .description()
                .description()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndescription\n}");
    }

    @Test
    void CharacterBuilder_DescriptionWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .description()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndescription\n}");
    }

    @Test
    void CharacterBuilder_ManyDescriptionWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .description()
                .description()
                .description()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndescription\n}");
    }

    @Test
    void CharacterBuilder_DescriptionAsHtml_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .descriptionAsHtml()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndescription(asHtml: true)\n}");
    }

    @Test
    void CharacterBuilder_ManyDescriptionAsHtml_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .descriptionAsHtml()
                .descriptionAsHtml()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndescription(asHtml: true)\n}");
    }

    @Test
    void CharacterBuilder_DescriptionAsHtmlWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .descriptionAsHtml()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndescription(asHtml: true)\n}");
    }

    @Test
    void CharacterBuilder_ManyDescriptionAsHtmlWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .descriptionAsHtml()
                .descriptionAsHtml()
                .descriptionAsHtml()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndescription(asHtml: true)\n}");
    }

    @Test
    void CharacterBuilder_Gender_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .gender()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ngender\n}");
    }

    @Test
    void CharacterBuilder_ManyGender_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .gender()
                .gender()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ngender\n}");
    }

    @Test
    void CharacterBuilder_GenderWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .gender()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ngender\n}");
    }

    @Test
    void CharacterBuilder_ManyGenderWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .gender()
                .gender()
                .gender()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ngender\n}");
    }

    @Test
    void CharacterBuilder_DateOfBirth_NoException() {
        //given
        FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.dateOfBirth)
                .allAndBuild();

        //when
        Character character = Character.getCharacterBuilder()
                .dateOfBirth(fuzzyDateField)
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyDateOfBirth_NoException() {
        //given
        FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.dateOfBirth)
                .allAndBuild();

        //when
        Character character = Character.getCharacterBuilder()
                .dateOfBirth(fuzzyDateField)
                .dateOfBirth(fuzzyDateField)
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
    }

    @Test
    void CharacterBuilder_DateOfBirthWithoutFieldName_NoException() {
        //given
        FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.dateOfBirth)
                .allAndBuild();

        //when
        Character character = Character.getCharacterBuilder()
                .dateOfBirth(fuzzyDateField)
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
    }

    @Test
    void CharacterBuilder_ManyDateOfBirthWithoutFieldName_NoException() {
        //given
        FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.dateOfBirth)
                .allAndBuild();

        //when
        Character character = Character.getCharacterBuilder()
                .dateOfBirth(fuzzyDateField)
                .dateOfBirth(fuzzyDateField)
                .dateOfBirth(fuzzyDateField)
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
    }

    @Test
    void CharacterBuilder_Age_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .age()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nage\n}");
    }

    @Test
    void CharacterBuilder_ManyAge_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .age()
                .age()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nage\n}");
    }

    @Test
    void CharacterBuilder_AgeWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .age()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nage\n}");
    }

    @Test
    void CharacterBuilder_ManyAgeWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .age()
                .age()
                .age()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nage\n}");
    }

    @Test
    void CharacterBuilder_AniListSiteUrl_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .aniListSiteUrl()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nsiteUrl\n}");
    }

    @Test
    void CharacterBuilder_ManyAniListSiteUrl_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .aniListSiteUrl()
                .aniListSiteUrl()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nsiteUrl\n}");
    }

    @Test
    void CharacterBuilder_AniListSiteUrlWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .aniListSiteUrl()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nsiteUrl\n}");
    }

    @Test
    void CharacterBuilder_ManyAniListSiteUrlWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .aniListSiteUrl()
                .aniListSiteUrl()
                .aniListSiteUrl()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nsiteUrl\n}");
    }

    @Test
    void CharacterBuilder_Favourites_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .favourites()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nfavourites\n}");
    }

    @Test
    void CharacterBuilder_ManyFavourites_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .favourites()
                .favourites()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nfavourites\n}");
    }

    @Test
    void CharacterBuilder_FavouritesWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .favourites()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nfavourites\n}");
    }

    @Test
    void CharacterBuilder_ManyFavouritesWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .favourites()
                .favourites()
                .favourites()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nfavourites\n}");
    }

    @Test
    void CharacterBuilder_ModNotes_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .modNotes()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nmodNotes\n}");
    }

    @Test
    void CharacterBuilder_ManyModNotes_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .modNotes()
                .modNotes()
                .buildCharacter();

        assertEquals(character.getCharacterString(), "character {\nmodNotes\n}");
    }

    @Test
    void CharacterBuilder_ModNotesWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .modNotes()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nmodNotes\n}");
    }

    @Test
    void CharacterBuilder_ManyModNotesWithoutFieldName_NoException() {
        //given

        //when
        Character character = Character.getCharacterBuilder()
                .modNotes()
                .modNotes()
                .modNotes()
                .buildCharacter();

        assertEquals(character.getCharacterStringWithoutFieldName(), "{\nmodNotes\n}");
    }
}